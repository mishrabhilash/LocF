package com.maststudio.locf;

/**
 * Created by mishrabhilash on 2/23/16.
 */
public class ApiKeyNotFoundException extends RuntimeException {

    private static final String message="No Api key was provided. Please provide a valid google api key for accessing location.\n" +
            "Steps to get Api key:\n" +
            "Go to the https://goo.gl/qWJHnU\n" +
            "Create or select a project.\n" +
            "Click Continue to Enable the API.\n" +
            "On the Credentials page, get a Browser key (and set the API Credentials). \n" +
            "Note: If you have an existing Browser key, you may use that key.\n" +
            "To prevent quota theft, secure your API key following these best practices.";
    public ApiKeyNotFoundException()
    {
        super(message);
    }

    public ApiKeyNotFoundException(String message)
    {
        super(message);
    }
}
