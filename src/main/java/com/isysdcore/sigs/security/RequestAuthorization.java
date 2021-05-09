package com.isysdcore.sigs.security;

import java.lang.annotation.*;

/**
 * @author Domingos M. Fernando
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequestAuthorization
{
}
