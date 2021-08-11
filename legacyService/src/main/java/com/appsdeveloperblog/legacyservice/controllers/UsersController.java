package com.appsdeveloperblog.legacyservice.controllers;



import com.appsdeveloperblog.legacyservice.response.UserRest;
import com.appsdeveloperblog.legacyservice.response.VerifyPasswordResponse;
import com.appsdeveloperblog.legacyservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/{username}")
    public UserRest getUser(@PathVariable("username") String username) {

        return usersService.getUserDetails(username);

    }

    @PostMapping("/{username}/verify-password")
    public VerifyPasswordResponse verifyUserPassword(@PathVariable("username") String username,
                                                     @RequestBody String password) {

        VerifyPasswordResponse returnValue = new VerifyPasswordResponse(false);

        UserRest user = usersService.getUserDetails(username, password);

        if (user != null) {
            returnValue.setResult(true);
        }

        return returnValue;
    }

}
