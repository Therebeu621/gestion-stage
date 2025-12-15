<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Mes Stages</title>
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
                    --success: #22c55e;
                    --danger: #ef4444;
                }

                * {
                    box-sizing: border-box;
                }

                body {
                    font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
                    display: flex;
                    justify-content: center;
                    align-items: flex-start;
                    min-height: 100vh;
                    margin: 0;
                    padding: 40px 20px;
                    background: radial-gradient(circle at 20% 20%, rgba(14, 165, 233, 0.12), transparent 35%),
                        radial-gradient(circle at 80% 10%, rgba(6, 182, 212, 0.12), transparent 30%),
                        linear-gradient(135deg, #0b1220 0%, #0f172a 50%, #0b1220 100%);
                    color: var(--text);
                }

                .container {
                    background: var(--card);
                    padding: 3rem;
                    border-radius: 18px;
                    border: 1px solid var(--border);
                    box-shadow: 0 22px 70px rgba(0, 0, 0, 0.4);
                    backdrop-filter: blur(8px);
                    width: 100%;
                    max-width: 900px;
                }

                h1 {
                    color: #fff;
                    margin-bottom: 1.5rem;
                    font-weight: 700;
                    font-size: 2rem;
                    letter-spacing: 0.5px;
                }

                p {
                    color: var(--muted);
                }

                .back-link {
                    display: inline-block;
                    margin-bottom: 30px;
                    text-decoration: none;
                    color: var(--accent);
                    font-weight: 600;
                    transition: color 0.2s;
                }

                .back-link:hover {
                    color: var(--accent-2);
                }

                .stage-card {
                    background: var(--input-bg);
                    border: 1px solid var(--border);
                    border-radius: 12px;
                    padding: 25px;
                    margin-bottom: 20px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    transition: transform 0.2s, border-color 0.2s;
                }

                .stage-card:hover {
                    border-color: rgba(14, 165, 233, 0.3);
                    transform: translateY(-2px);
                }

                .stage-info h3 {
                    margin: 0 0 8px;
                    color: #fff;
                    font-size: 1.25rem;
                }

                .stage-info p {
                    margin: 4px 0;
                    color: var(--muted);
                    font-size: 0.95em;
                }

                .toggle-btn {
                    background: transparent;
                    border: 2px solid;
                    padding: 8px 16px;
                    border-radius: 999px;
                    cursor: pointer;
                    font-weight: 700;
                    font-size: 0.85em;
                    transition: all 0.2s;
                    text-transform: uppercase;
                    letter-spacing: 0.05em;
                }

                .btn-visible {
                    border-color: var(--success);
                    color: var(--success);
                    box-shadow: 0 0 10px rgba(34, 197, 94, 0.1);
                }

                .btn-visible:hover {
                    background: rgba(34, 197, 94, 0.1);
                    box-shadow: 0 0 15px rgba(34, 197, 94, 0.2);
                }

                .btn-hidden {
                    border-color: var(--muted);
                    color: var(--muted);
                }

                .btn-hidden:hover {
                    border-color: var(--text);
                    color: var(--text);
                    background: rgba(255, 255, 255, 0.05);
                }

                .status-text {
                    display: block;
                    margin-top: 8px;
                    font-size: 0.8em;
                    text-align: center;
                }
            </style>
        </head>

        <body>
            <div class="container">
                <a href="${pageContext.request.contextPath}/mvc/" class="back-link">← Retour accueil</a>
                <h1>Mes Stages</h1>
                <p>Gérez ici la visibilité de vos stages pour les autres étudiants.</p>
                <br>

                <c:if test="${empty stages}">
                    <div style="text-align: center; padding: 40px; color: var(--muted);">
                        Aucun stage trouvé pour votre adresse email.
                    </div>
                </c:if>

                <c:forEach var="stage" items="${stages}" varStatus="status">
                    <div class="stage-card">
                        <div class="stage-info">
                            <h3>${stage.nomEtablissementAccueil}</h3>
                            <p>${stage.communeEtablissement} (${stage.codePostal})</p>
                            <p>Du ${stage.dateDebut} au ${stage.dateFin}</p>
                        </div>
                        <div class="toggle-form" style="text-align: center;">
                            <form action="${pageContext.request.contextPath}/mvc/my-stages/toggle-accord" method="post">
                                <input type="hidden" name="stageIndex" value="${status.index}" />
                                <button type="submit" class="toggle-btn ${stage.accord ? 'btn-visible' : 'btn-hidden'}">
                                    ${stage.accord ? 'VISIBLE' : 'MASQUÉ'}
                                </button>
                                <span class="status-text"
                                    style="color: ${stage.accord ? 'var(--success)' : 'var(--muted)'}">
                                    ${stage.accord ? 'Visible sur la fiche' : 'Non visible'}
                                </span>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </body>

        </html>