package com.appsdeveloperblog.keycloak;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author : zikoz
 * @created : 10 Aug, 2021
 */

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public interface UserApiService {

    @GET
    @Path("/{username}")
    User getUserDetails(@PathParam("username") String username);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}/verify-password")
    VerifyPasswordResponse verifyPassword(@PathParam("username") String username, String password);


}
