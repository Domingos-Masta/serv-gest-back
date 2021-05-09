package com.isysdcore.sigs.security;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Domingos M. Fernando
 */
public class UserInformation
{

    private Map<String, String> map = new HashMap<>();

    public UserInformation()
    {
    }

    public Map<String, String> getMap()
    {
        return map;
    }

    public UserInformation setMap(Map<String, String> map)
    {
        this.map = map;
        return this;
    }
}
