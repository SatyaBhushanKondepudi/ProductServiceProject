package org.bhushan.productserviceproject.authCommons;

import org.apache.catalina.User;
import org.bhushan.productserviceproject.dtos.UserDto;
import org.bhushan.productserviceproject.exceptions.InvalidTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationCommons {

    private RestTemplate restTemplate ;

    public AuthenticationCommons(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String token){
        System.out.println("Validating the Token Value");
        UserDto userDto =  restTemplate.getForObject(
                "http://USERSERVICE/user/validate?token=" + token,
                UserDto.class);
        if(userDto == null){
            throw new InvalidTokenException("Invalid Token Passed");
        }
        return userDto;
    }
}
