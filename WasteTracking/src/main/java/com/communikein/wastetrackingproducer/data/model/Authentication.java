package com.communikein.wastetrackingproducer.data.model;

/**
 * This class represents the authentication necessary to access to the APIs
 */
public class Authentication {

    private String email;
    private String password;

    /**
     * Create a new Authentication object needed to authenticate to the API
     *
     * @param email the user email
     * @param password the user password
     */
    public Authentication(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
