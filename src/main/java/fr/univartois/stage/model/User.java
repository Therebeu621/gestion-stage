package fr.univartois.stage.model;

public class User {
    private String login;
    private String password;
    private String prenom;
    private String email;
    private Role role;

    public enum Role {
        ADMIN, STUDENT
    }

    public User(String login, String password, String prenom, Role role) {
        this.login = login;
        this.password = password;
        this.prenom = prenom;
        this.role = role;
    }

    // Getters and Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
