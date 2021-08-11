package com.appsdeveloperblog.legacyservice.service;

import com.appsdeveloperblog.legacyservice.response.UserRest;

public interface UsersService {
   UserRest getUserDetails(String userName, String password);
   UserRest getUserDetails(String userName);
}
