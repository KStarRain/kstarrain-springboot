package com.kstarrain.api.url;


public class UrlMapping {

    private UrlMapping() {
    }

    private static final String API_URL_PREFIX = "/api";

    public static final String USER_IMPORT      =   API_URL_PREFIX + "/user/import";
    public static final String USER_LIST        =   API_URL_PREFIX + "/user/list";
    public static final String USER_DETAIL      =   API_URL_PREFIX + "/user/detail";
    public static final String USER_EXPORT      =   API_URL_PREFIX + "/user/export";

}