package com.appsdeveloperblog.ws.api.resourceserver.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : zikoz
 * @created : 10 Jul, 2021
 */

@Getter @Setter @AllArgsConstructor @ToString
public class UserRest {
    private String userFirstName;
    private String userLastName;
    private String userId;
}
