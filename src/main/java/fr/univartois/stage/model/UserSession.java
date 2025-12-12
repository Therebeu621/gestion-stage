package fr.univartois.stage.model;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named("userSession")
public class UserSession implements Serializable {

    private User user;

    public boolean isLoggedIn() {
        return user != null;
    }

    public boolean isResponsible() {
        return isLoggedIn() && user.getRole() == User.Role.RESPONSIBLE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void logout() {
        this.user = null;
    }
}
