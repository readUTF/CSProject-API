package com.connor.csprojectapi.utils;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordHashing {


    MessageDigest messageDigest;

    @SneakyThrows
    //When the class is instantiated, a new instance of the algorithm is created.
    public PasswordHashing()  {
        messageDigest = MessageDigest.getInstance("SHA-256");
    }

    /**
     * Generate a sha-256 hash based on the inputted string
     * @param toHash - The string/password to be hashed
     * @return hashed string
     */
    public String generateHash(String toHash) {
        byte[] bytes = toHash.getBytes(StandardCharsets.UTF_8);
        return bytesToHex(messageDigest.digest(bytes));
    }

    /**
     * Checks if the password entered matches the hashed password
     * @param password Un-hashed password from the user
     * @param hash - The hashed password stored in the users profile
     * @return true if the password and hash match
     */
    public boolean isValidPassword(String password, String hash) {
        return generateHash(password).equalsIgnoreCase(hash);
    }

    /**
     * Converts the inputted byte array into a string
     * @param hash Hash byte array
     * @return the has in string form
     */
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
