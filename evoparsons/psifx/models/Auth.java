package evoparsons.psifx.models;

public class Auth {
    public final String login;
    public final String host;
    public final int port;
    public Auth(String login, String host, int port) {
        this.login = login;
        this.host = host;
        this.port = port;
    }
}