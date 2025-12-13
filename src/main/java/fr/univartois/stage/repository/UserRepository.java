package fr.univartois.stage.repository;

import fr.univartois.stage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @jakarta.annotation.Resource(name = "jdbc/StageDB")
    private javax.sql.DataSource dataSource;

    public Optional<User> findByLogin(String login) {
        String sql = "SELECT login, password, role_name, prenom, email FROM users WHERE login = ?";
        try (java.sql.Connection conn = dataSource.getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String roleName = rs.getString("role_name");
                    User.Role role = User.Role.valueOf(roleName);
                    User user = new User(
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("prenom"),
                            role);
                    user.setEmail(rs.getString("email"));
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
