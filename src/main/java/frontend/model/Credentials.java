package frontend.model;

public class Credentials {

    private Object username;

    private Object password;

    public Credentials() {
    }

    public Credentials(Object username, Object password) {
        this.username = username;
        this.password = password;
    }

    public Object getUsername() {
        return username;
    }

    public Object getPassword() {
        return password;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public void setPassword(Object password) {
        this.password = password;
    }
}
