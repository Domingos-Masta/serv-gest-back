package com.isysdcore.sigs.auth;

import java.io.Serializable;

/**
 *
 * @author domingos.fernando
 */
public class AuthResponse implements Serializable
{

    private String userName;
    private String token;
    private Long id;

    public AuthResponse(Long id, String userName, String token)
    {
        this.userName = userName;
        this.token = token;
        this.id = id;
    }

    public AuthResponse(String userName, String token)
    {
        this.userName = userName;
        this.token = token;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public AuthResponse setUserName(String userName)
    {
        this.userName = userName;
        return this;
    }

    public String getToken()
    {
        return token;
    }

    public AuthResponse setToken(String token)
    {
        this.token = token;
        return this;
    }
}
