package com.homework.transactionservice.validation;

/**
 * A collection of common regular expression
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class PatternRegex {

    /**
     * 16 char
     */
    public static final String COMMON_REGEX_16 = "^[a-zA-Z0-9_ ]{1,16}$";

    /**
     * 32 char
     */
    public static final String COMMON_REGEX_32 = "^[a-zA-Z0-9_ ]{1,32}$";

    /**
     * 64 char
     */
    public static final String COMMON_REGEX_64 = "^[a-zA-Z0-9_ ]{1,64}$";

    /**
     * 128 char
     */
    public static final String COMMON_REGEX_128 = "^[a-zA-Z0-9_ ]{1,128}$";

    /**
     * 256 char
     */
    public static final String COMMON_REGEX_256 = "^[a-zA-Z0-9_ ]{1,256}$";

}
