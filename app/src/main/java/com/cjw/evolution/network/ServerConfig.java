package com.cjw.evolution.network;

/**
 * Created by CJW on 2016/7/19.
 */
public final class ServerConfig {

    public static final String DRIBBBLE_CLIENT_ID = "d8cd884a992f3d2b24dc41ab252a1f034613f357e46f9d148e413e5617bcf97f";
    public static final String DRIBBBLE_CLIENT_SECRET = "2cf2c437cdb1e35c0b1df7d9adddb13ba747a606ff8fcdaac644266689c1cb1f";
    public static final String LOGIN_CALLBACK = "https://v1sk.github.io";

    public static final String AUTHORIZE_HOST = "https://dribbble.com";
    public static final String API_HOST = "https://api.dribbble.com/v1/";

    public static final String LOGIN_URL = "https://dribbble.com/oauth/authorize?client_id="
            + DRIBBBLE_CLIENT_ID
            + "&redirect_uri=" + LOGIN_CALLBACK
            + "&scope=public+write+comment+upload";

}
