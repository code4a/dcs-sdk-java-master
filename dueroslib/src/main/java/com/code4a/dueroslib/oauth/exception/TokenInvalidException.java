package com.code4a.dueroslib.oauth.exception;

/**
 * Created by jiang on 2018/2/1.
 */

public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException(){
        super("token is invalid, please call oauth api!");
    }
}
