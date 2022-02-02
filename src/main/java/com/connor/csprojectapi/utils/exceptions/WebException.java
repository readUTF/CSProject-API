package com.connor.csprojectapi.utils.exceptions;

import com.connor.csprojectapi.utils.MapUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ResponseStatus(HttpStatus.CHECKPOINT)
public class WebException extends RuntimeException {

    String errorCode;
    Object invalidData;

    /**
     * This is an exception returned within rest api mappings and is used by spring
     * to return information when invalid data is provided to the api.
     * The two parameters are appended together so that the client interacting wit the api
     * can determine the type of error, as well as the invalid data
     *
     * @param errorCode   the error code relating to the exception
     * @param invalidData the data originally provided to the api
     */
    public WebException(String errorCode, Object invalidData) {
        super(errorCode + ":" + invalidData);
        this.errorCode = errorCode;
        this.invalidData = invalidData;
    }

    /**
     * Converts the Exception into a Map as spring can convert this into a json
     * object and return it in the http response.
     * @return Serialised Web Exception
     */
    public HashMap<String, Object> getData() {
        return new MapUtil<String, Object>("error_code", errorCode).add("invalid_data", invalidData).build();
    }
}
