<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Connexion</title>
            <style>
                body {
                    font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                    margin: 0;
                }

                .login-container {
                    background: white;
                    padding: 2.5rem;
                    border-radius: 12px;
                    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
                    width: 100%;
                    max-width: 350px;
                    transition: transform 0.3s ease;
                }

                .login-container:hover {
                    transform: translateY(-5px);
                }

                h2 {
                    text-align: center;
                    color: #2d3748;
                    margin-bottom: 2rem;
                    font-weight: 600;
                }

                .form-group {
                    margin-bottom: 1.5rem;
                }

                label {
                    display: block;
                    margin-bottom: 0.5rem;
                    color: #4a5568;
                    font-size: 0.9rem;
                    font-weight: 500;
                }

                input {
                    width: 100%;
                    padding: 0.75rem 1rem;
                    border: 2px solid #e2e8f0;
                    border-radius: 8px;
                    box-sizing: border-box;
                    font-size: 1rem;
                    transition: border-color 0.2s, box-shadow 0.2s;
                    outline: none;
                }

                input:focus {
                    border-color: #4299e1;
                    box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.2);
                }

                button {
                    width: 100%;
                    padding: 0.875rem;
                    background: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
                    color: white;
                    border: none;
                    border-radius: 8px;
                    cursor: pointer;
                    font-size: 1rem;
                    font-weight: 600;
                    letter-spacing: 0.5px;
                    transition: all 0.3s ease;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                }

                button:hover {
                    background: linear-gradient(to right, #00c6fb 0%, #005bea 100%);
                    transform: translateY(-2px);
                    box-shadow: 0 7px 14px rgba(0, 0, 0, 0.15);
                }

                button:active {
                    transform: translateY(0);
                }

                .error {
                    background-color: #fff5f5;
                    color: #c53030;
                    padding: 0.75rem;
                    border-radius: 6px;
                    text-align: center;
                    margin-bottom: 1.5rem;
                    font-size: 0.9rem;
                    border: 1px solid #feb2b2;
                }
            </style>
        </head>

        <body>
            <div class="login-container">
                <h2>Connexion</h2>

                <c:if test="${param.error != null}">
                    <div class="error">Identifiants incorrects</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/mvc/auth/login" method="post">
                    <div class="form-group">
                        <label for="login">Identifiant :</label>
                        <input type="text" id="login" name="login" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Mot de passe :</label>
                        <input type="password" id="password" name="password" required>
                    </div>

                    <button type="submit">Se connecter</button>
                </form>
            </div>
        </body>

        </html>