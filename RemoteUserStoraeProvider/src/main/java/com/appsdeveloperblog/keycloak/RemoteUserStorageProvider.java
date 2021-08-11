package com.appsdeveloperblog.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.UserCredentialStore;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

/**
 * @author : zikoz
 * @created : 10 Aug, 2021
 */

public class RemoteUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

    private KeycloakSession keycloakSession;
    private ComponentModel componentModel;
    private UserApiService userApiService;

    public RemoteUserStorageProvider(KeycloakSession keycloakSession, ComponentModel componentModel, UserApiService userApiService) {
        this.keycloakSession = keycloakSession;
        this.componentModel = componentModel;
        this.userApiService = userApiService;
    }

    @Override
    public void close() {

    }


    @Override
    public UserModel getUserById(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realmModel) {

        UserModel returnedValue = null;
        User user = userApiService.getUserDetails(username);

        if(user!=null){
            returnedValue = createUserModel(username, realmModel);
        }

        return returnedValue;
    }

    private UserModel createUserModel(String username, RealmModel realmModel){
        return new AbstractUserAdapter(keycloakSession, realmModel, componentModel) {
            @Override
            public String getUsername() {
                return username;
            }
        };
    }

    @Override
    public UserModel getUserByEmail(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String credentialType) {
        if(!supportsCredentialType(credentialType)) return false;
        return !getCredentialsStore().getStoredCredentialsByType(realmModel, userModel, credentialType).isEmpty();
    }

    private UserCredentialStore getCredentialsStore(){
        return keycloakSession.userCredentialManager();
    }

    @Override
    public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
        System.out.println("username: " + userModel.getUsername() == null);
        VerifyPasswordResponse verifyPasswordResponse = userApiService.verifyPassword(userModel.getUsername(), credentialInput.getChallengeResponse());

        if(verifyPasswordResponse == null) return false;
        return verifyPasswordResponse.getResult();
    }
}
