package br.com.pdz.auth.model.response;

public class LoginResponse {

    private String type;
    private String token;

    public LoginResponse(String type, String token) {
        this.type = type;
        this.token = token;
    }

    @Deprecated
    public LoginResponse() {
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}
