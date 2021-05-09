/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.user;

/**
 *
 * @author domingos.fernando
 */
public class UserPasswordUpdater
{

    private String userEmail;
    private String newPassword;
    private String oldPassword;

    public UserPasswordUpdater()
    {
    }

    public UserPasswordUpdater(String userEmail, String newPassword, String oldPassword)
    {
        this.userEmail = userEmail;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

}
