/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.security;

import java.util.Date;

/**
 *
 * @author domingos.fernando
 */
public class JwtToken {

    private String userName;
    private String tokenType;
    private String token;
    private Date validade;

    public JwtToken() {
    }

    public JwtToken(String userName, String tokenType, String token, Date validade) {
        this.userName = userName;
        this.tokenType = tokenType;
        this.token = token;
        this.validade = validade;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "JwtToken{" + "userName=" + userName + ", tokenType=" + tokenType + ", token=" + token + ", validade=" + validade + '}';
    }

}
