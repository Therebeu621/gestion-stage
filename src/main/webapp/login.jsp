<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Connexion</title>
            <style>
                :root {
                    --bg: #0b1220;
                    --card: rgba(15, 23, 42, 0.9);
                    --border: rgba(148, 163, 184, 0.08);
                    --text: #e2e8f0;
                    --muted: #94a3b8;
                    --accent: #0ea5e9;
                    --accent-2: #06b6d4;
                    --input-bg: rgba(15, 23, 42, 0.6);
                }

                * {
                    box-sizing: border-box;
                }

                body {
                    font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                    margin: 0;
                    background: radial-gradient(circle at 20% 20%, rgba(14, 165, 233, 0.12), transparent 35%),
                        radial-gradient(circle at 80% 10%, rgba(6, 182, 212, 0.12), transparent 30%),
                        linear-gradient(135deg, #0b1220 0%, #0f172a 50%, #0b1220 100%);
                    color: var(--text);
                }

                .login-container {
                    background: var(--card);
                    padding: 3rem;
                    border-radius: 18px;
                    border: 1px solid var(--border);
                    box-shadow: 0 22px 70px rgba(0, 0, 0, 0.4);
                    backdrop-filter: blur(8px);
                    width: 100%;
                    max-width: 400px;
                }

                h2 {
                    text-align: center;
                    color: #fff;
                    margin-bottom: 2.5rem;
                    font-weight: 700;
                    font-size: 2rem;
                    letter-spacing: 0.5px;
                }

                .form-group {
                    margin-bottom: 1.5rem;
                }

                label {
                    display: block;
                    margin-bottom: 0.75rem;
                    color: var(--muted);
                    font-size: 0.9rem;
                    font-weight: 600;
                    letter-spacing: 0.02em;
                }

                input {
                    width: 100%;
                    padding: 1rem;
                    background: var(--input-bg);
                    border: 1px solid var(--border);
                    border-radius: 10px;
                    color: white;
                    font-size: 1rem;
                    transition: all 0.2s ease;
                    outline: none;
                }

                input:focus {
                    border-color: var(--accent);
                    box-shadow: 0 0 0 2px rgba(14, 165, 233, 0.2);
                    background: rgba(15, 23, 42, 0.8);
                }

                button {
                    width: 100%;
                    padding: 1rem;
                    background: linear-gradient(135deg, var(--accent) 0%, var(--accent-2) 100%);
                    color: #0b1220;
                    border: none;
                    border-radius: 10px;
                    cursor: pointer;
                    font-size: 1.1rem;
                    font-weight: 700;
                    margin-top: 1rem;
                    transition: all 0.3s ease;
                    text-transform: uppercase;
                    letter-spacing: 0.05em;
                }

                button:hover {
                    box-shadow: 0 0 20px rgba(14, 165, 233, 0.4);
                    transform: translateY(-2px);
                }

                .error {
                    background: rgba(239, 68, 68, 0.15);
                    border: 1px solid rgba(239, 68, 68, 0.2);
                    color: #fca5a5;
                    padding: 1rem;
                    border-radius: 10px;
                    text-align: center;
                    margin-bottom: 2rem;
                    font-size: 0.95rem;
                }
            </style>
        </head>

        <body>
            <div class="login-container">
                <h2>Connexion</h2>

                <c:if test="${param.error != null}">
                    <div class="error">Identifiants incorrects</div>
                </c:if>

                <form action="j_security_check" method="post">
                    <div class="form-group">
                        <label for="login">Identifiant :</label>
                        <input type="text" id="login" name="j_username" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Mot de passe :</label>
                        <input type="password" id="password" name="j_password" required>
                    </div>

                    <button type="submit">Se connecter</button>
                </form>
            </div>
        </body>

        </html>