/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.util;

import java.time.Year;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author domingos.fernando
 */
public class Constants
{

    public static final String SECRET_KEY = "sDt+gt45Cta/oEsrDS/KWyOhIL680kbRRQLfmKdFY4EpCbYp1kierUa8kgYQMcXz6rqFtavv9kKMqMtBFsmoBg==";
    public static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile("^[a-z0-9!#$%&'*+\\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$", Pattern.CASE_INSENSITIVE);

    public static final String PREFIX_CANDIDATE_CODE = "P" + Year.now().getValue() % 1000;
    public static final String MIDDLE_CANDIDATE_SELF_REGISTER_CODE = "C";
    public static final String MIDDLE_CANDIDATE_ADMIN_REGISTER_CODE = "A";
    public static final Integer SUFIX_CANDIDATE_CODE = 1000000000;

    public static final String MIDDLE_EXTERN_CANDIDATE_CODE = "E";

    @Value("${enviroment.staging}")
    private static String enviromentValue;
    public static final String DEFAULT_APP_URL_BASE = "/servgest";
    public static final String DEFAULT_APP_API_VERSION = "/v1";
    public static final String APP_NAME = "/api";

}
