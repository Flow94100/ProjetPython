package com.example.frozotte.projetpython.security;

import java.net.URLEncoder;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Abraham on 14/04/2016.
 */
public class PostData {

    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}

