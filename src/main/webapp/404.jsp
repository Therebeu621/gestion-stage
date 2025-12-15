<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Page non trouvée - 404</title>
        <style>
            :root {
                --bg: #0b1220;
                --card: rgba(15, 23, 42, 0.9);
                --border: rgba(148, 163, 184, 0.08);
                --text: #e2e8f0;
                --muted: #94a3b8;
                --accent: #0ea5e9;
                --accent-2: #06b6d4;
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

            .error-container {
                background: var(--card);
                padding: 3rem;
                border-radius: 18px;
                border: 1px solid var(--border);
                box-shadow: 0 22px 70px rgba(0, 0, 0, 0.4);
                backdrop-filter: blur(8px);
                text-align: center;
                max-width: 500px;
            }

            h1 {
                font-size: 6rem;
                margin: 0;
                background: linear-gradient(135deg, var(--accent) 0%, var(--accent-2) 100%);
                -webkit-background-clip: text;
                background-clip: text;
                -webkit-text-fill-color: transparent;
                font-weight: 800;
            }

            h2 {
                margin-top: 1rem;
                margin-bottom: 1rem;
                font-size: 2rem;
            }

            p {
                color: var(--muted);
                line-height: 1.6;
                margin-bottom: 2.5rem;
            }

            .btn {
                display: inline-block;
                padding: 1rem 2rem;
                background: linear-gradient(135deg, var(--accent) 0%, var(--accent-2) 100%);
                color: #0b1220;
                text-decoration: none;
                border-radius: 10px;
                font-weight: 700;
                text-transform: uppercase;
                letter-spacing: 0.05em;
                transition: all 0.3s ease;
            }

            .btn:hover {
                box-shadow: 0 0 20px rgba(14, 165, 233, 0.4);
                transform: translateY(-2px);
            }
        </style>
    </head>

    <body>
        <div class="error-container">
            <h1>404</h1>
            <h2>Page non trouvée</h2>
            <p>Oups ! La page que vous recherchez semble avoir disparu ou n'a jamais existé.</p>
            <a href="${pageContext.request.contextPath}/mvc/" class="btn">Retour à l'accueil</a>
        </div>
    </body>

    </html>