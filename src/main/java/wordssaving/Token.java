package wordssaving;

import exceptions.TokenIsRequiredException;

public class Token {
    private String token;

    public Token(String token) throws TokenIsRequiredException {
        if (token.equals("Not found")) throw new TokenIsRequiredException();
        else
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
