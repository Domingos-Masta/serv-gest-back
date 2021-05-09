package com.isysdcore.sigs.auth;

import java.io.Serializable;

/**
 *
 * @author domingos.fernando
 */
public class UserLoginDTO implements Serializable
{

    private String email;
    private String password;
    private boolean rememberMe;

    public UserLoginDTO()
    {
    }

    public UserLoginDTO(String email, String password, boolean tenantOrClientId)
    {
        this.email = email;
        this.password = password;
        this.rememberMe = tenantOrClientId;
    }

    public String getEmail()
    {
        return email;
    }

    public UserLoginDTO setUserName(String userName)
    {
        this.email = userName;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public UserLoginDTO setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public boolean getRememberMe()
    {
        return rememberMe;
    }

    public UserLoginDTO setTenantOrClientId(boolean tenantOrClientId)
    {
        this.rememberMe = tenantOrClientId;
        return this;
    }
}
