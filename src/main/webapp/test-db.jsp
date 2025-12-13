<%@ page import="javax.naming.InitialContext" %>
    <%@ page import="javax.sql.DataSource" %>
        <%@ page import="java.sql.Connection" %>
            <%@ page import="java.sql.PreparedStatement" %>
                <%@ page import="java.sql.ResultSet" %>
                    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
                        <html>

                        <head>
                            <title>DB User List</title>
                        </head>

                        <body>
                            <h1>Database Users</h1>
                            <table border="1">
                                <tr>
                                    <th>Login</th>
                                    <th>Password</th>
                                    <th>Role</th>
                                    <th>Prenom</th>
                                </tr>
                                <% try { InitialContext ctx=new InitialContext(); DataSource ds=(DataSource)
                                    ctx.lookup("java:comp/env/jdbc/StageDB"); try (Connection conn=ds.getConnection()) {
                                    String sql="SELECT * FROM users" ; try (PreparedStatement
                                    pstmt=conn.prepareStatement(sql); ResultSet rs=pstmt.executeQuery()) { while
                                    (rs.next()) { %>
                                    <tr>
                                        <td>
                                            <%= rs.getString("login") %>
                                        </td>
                                        <td>
                                            <%= rs.getString("password") %>
                                        </td>
                                        <td>
                                            <%= rs.getString("role_name") %>
                                        </td>
                                        <td>
                                            <%= rs.getString("prenom") %>
                                        </td>
                                    </tr>
                                    <% } } } } catch (Exception e) { %>
                                        <tr>
                                            <td colspan="4">ERROR: <%= e.getMessage() %>
                                            </td>
                                        </tr>
                                        <% e.printStackTrace(); } %>
                            </table>
                        </body>

                        </html>