package com.baidu.duer.dcs.oauth.api;

/**
 * Created by jiang on 2018/1/29.
 */

public class OauthClientCredentialsInfo {


    /**
     * access_token : 24.c46fac9939d87a2f4b5ae300a63808a7.2592000.1519801089.282335-10749100
     * session_key : 9mzdWET9fzl7Es7JEfZx3NZHIPx0XQKa22uulLdDrHU7aO/dPB+yFokyw3JqlqecqiGr37UpuwG8M1iyVokiaoI/kNiZAA==
     * scope : public wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower bnstest_fasf lpq_开放 cop_helloScope ApsMis_fangdi_permission
     * refresh_token : 25.bc814c149a65e9db44bdb4f376d84303.315360000.1832569089.282335-10749100
     * session_secret : 563f9d8371aec17612abfd1c0970223d
     * expires_in : 2592000
     */

    private String access_token;
    private String session_key;
    private String scope;
    private String refresh_token;
    private String session_secret;
    private long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getSession_secret() {
        return session_secret;
    }

    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "OauthClientCredentialsInfo{" +
                "access_token='" + access_token + '\'' +
                ", session_key='" + session_key + '\'' +
                ", scope='" + scope + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", session_secret='" + session_secret + '\'' +
                ", expires_in=" + expires_in +
                '}';
    }
}
