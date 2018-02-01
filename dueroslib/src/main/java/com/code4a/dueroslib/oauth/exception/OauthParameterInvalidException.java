package com.code4a.dueroslib.oauth.exception;

/**
 * Created by jiang on 2018/2/1.
 */

public class OauthParameterInvalidException extends RuntimeException {

    public OauthParameterInvalidException(){
        super("oauth parameter is invalid, please call init at first!");
    }
}
