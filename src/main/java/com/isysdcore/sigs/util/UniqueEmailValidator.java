/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.util;

import com.isysdcore.sigs.user.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author domingos.fernando
 */
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>
{

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context)
    {
        return userRepository.findByEmail(email) == null;
    }
}
