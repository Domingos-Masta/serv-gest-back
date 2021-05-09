/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.exceptions;

/**
 *
 * @author domingos.fernando
 */
public class EntityNotFoundException extends RuntimeException
{

    public EntityNotFoundException(Object entity, Object id)
    {
        super("Could not find " + entity.getClass().getName() + ", By ID:" + id);
    }
}
