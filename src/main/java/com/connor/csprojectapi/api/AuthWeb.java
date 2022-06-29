package com.connor.csprojectapi.api;

import com.connor.csprojectapi.data.Authentication;
import com.connor.csprojectapi.data.Profile;
import com.connor.csprojectapi.data.Token;
import com.connor.csprojectapi.repositories.AuthRepository;
import com.connor.csprojectapi.repositories.ProfileRepository;
import com.connor.csprojectapi.repositories.TokenRepository;
import com.connor.csprojectapi.utils.HashMapBuilder;
import com.github.javafaker.Faker;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/authentication/")
public class AuthWeb {

    private static final Faker FAKER = Faker.instance();

    private TokenRepository tokenRepo;
    private AuthRepository authRepo;
    private ProfileRepository profileRepo;

    public AuthWeb(TokenRepository tokenRepo, AuthRepository authRepo, ProfileRepository profileRepo) {
        this.tokenRepo = tokenRepo;
        this.authRepo = authRepo;
        this.profileRepo = profileRepo;
        System.out.println("test");
    }

    //Marks the method as handling HTTP GET requests
    @PutMapping("register")
    public ResponseEntity<?> createAccount(String email, String password) {

        //Check within the database if an entity exists with the email provided
        if (authRepo.existsAuthenticationByEmail(email)) {
            //Respond with a BAD_REQUEST response and the text 'Account exists;
            return new ResponseEntity<>("Account exists", HttpStatus.BAD_REQUEST);
        }
        //Uses the library 'Faker' to generate a random username
        String username = FAKER.superhero().prefix() +
                FAKER.name().firstName() + FAKER.address().buildingNumber();

        //Uses SHA256 to hash the users password for secure storage
        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        //Create a new authentication entity and save it to the authentication table
        Authentication auth = authRepo.save(new Authentication(email, hashedPassword));

        //Create a new profile entity and save it to the
        Profile profile = profileRepo.save(
                new Profile(auth.getId(), username, LocalDateTime.now(), 0, 0));

        //Create a new token entity and save it to the database
        Token token = tokenRepo.save(new Token(
                auth.getId(), UUID.randomUUID(), LocalDateTime.now(), ""));

        //Construct a map of values to be turned into a json object and return
        HashMap<String, Object> data = new HashMapBuilder<String, Object>()
                .add("token", token.getToken().toString())
                .add("profile_id", profile.getId())
                .add("token_expiry", token.getGeneratedTime().plusDays(30))
                .build();
        return new ResponseEntity<>(data, HttpStatus.CREATED); //Respond with the id's of all entities created
    }

    /**
     * @param email    - Account email
     * @param password - Account password
     * @return Token, profile id and token expiry date
     */
    @GetMapping("login")
    //Define the api request to take 2 strings (email and password) as an input
    public ResponseEntity<?> login(String email, String password) {
        //Find an existing account using the provided email
        Authentication authentication = authRepo.findAuthenticationByEmail(email).orElse(null);

        //If not account is found then respond with Email not found and BAD_REQUEST response
        if(authentication == null) return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);

        //Hash the provided password, check if it matches the password stored within the found profile
        if(!Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString().equalsIgnoreCase(authentication.getPassword())) {
            //Password does not match, respond accordingly with BAD_REQUEST & "Invalid Password"
            return new ResponseEntity<>("Invalid Password", HttpStatus.BAD_REQUEST);
        }

        //Generate token linking the users' login attempt and the authentication entity
        Token token = tokenRepo.save(new Token(authentication.getId(), UUID.randomUUID(), LocalDateTime.now(), ""));

        //Find user profile from the authentication profile
        Profile profile = profileRepo.getProfileByAuthId(authentication.getId());

        //Respond with token, profileID and the expiration of the token
        HashMap<String, Object> responseData = new HashMapBuilder<String, Object>()
                .add("token", token.getToken())
                .add("profile_id", profile.getId())
                .add("token_expiry", token.getGeneratedTime().plusDays(30))
                .build();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }



}
