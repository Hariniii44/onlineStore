import java.util.List;

public class User {
    private String username;
    private String password;
    private boolean firstPurchase;

    //constructors

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.firstPurchase = true;
    }

    //getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    //setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }
}
