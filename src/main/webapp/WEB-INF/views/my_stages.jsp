<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Mes Stages</title>
            <style>
                body {
                    font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
                    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                    margin: 0;
                    padding: 40px;
                    min-height: 100vh;
                }

                .container {
                    max-width: 900px;
                    margin: 0 auto;
                    background: white;
                    padding: 40px;
                    border-radius: 12px;
                    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
                }

                h1 {
                    color: #2d3748;
                    margin-bottom: 24px;
                }

                .stage-card {
                    background: #fff;
                    border: 1px solid #e2e8f0;
                    border-radius: 8px;
                    padding: 20px;
                    margin-bottom: 20px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
                }

                .stage-info h3 {
                    margin: 0 0 5px;
                    color: #1a202c;
                }

                .stage-info p {
                    margin: 0;
                    color: #718096;
                    font-size: 0.9em;
                }

                .toggle-form {
                    display: flex;
                    align-items: center;
                }

                .toggle-btn {
                    background: none;
                    border: 2px solid;
                    padding: 8px 16px;
                    border-radius: 20px;
                    cursor: pointer;
                    font-weight: 600;
                    font-size: 0.9em;
                    transition: all 0.2s;
                }

                .btn-visible {
                    border-color: #48bb78;
                    color: #48bb78;
                }

                .btn-visible:hover {
                    background-color: #48bb78;
                    color: white;
                }

                .btn-hidden {
                    border-color: #a0aec0;
                    color: #a0aec0;
                }

                .btn-hidden:hover {
                    background-color: #a0aec0;
                    color: white;
                }

                .back-link {
                    display: inline-block;
                    margin-bottom: 20px;
                    text-decoration: none;
                    color: #4299e1;
                    font-weight: 600;
                }
            </style>
        </head>

        <body>
            <div class="container">
                <a href="${pageContext.request.contextPath}/mvc/" class="back-link">← Retour accueil</a>
                <h1>Mes Stages</h1>

                <p>Gérez ici la visibilité de vos stages pour les autres étudiants.</p>

                <c:if test="${empty stages}">
                    <p>Aucun stage trouvé pour votre adresse email.</p>
                </c:if>

                <c:forEach var="stage" items="${stages}" varStatus="status">
                    <div class="stage-card">
                        <div class="stage-info">
                            <h3>${stage.nomEtablissementAccueil}</h3>
                            <p>${stage.communeEtablissement} (${stage.codePostal})</p>
                            <p>Du ${stage.dateDebut} au ${stage.dateFin}</p>
                        </div>
                        <div class="toggle-form">
                            <form action="${pageContext.request.contextPath}/mvc/my-stages/toggle-accord" method="post">
                                <input type="hidden" name="stageIndex" value="${status.index}" />
                                <button type="submit" class="toggle-btn ${stage.accord ? 'btn-visible' : 'btn-hidden'}">
                                    ${stage.accord ? 'VISIBLE' : 'MASQUÉ'}
                                </button>
                                ${stage.accord ? '<br><small style="color:#48bb78">Visible sur la fiche</small>' :
                                '<br><small style="color:#a0aec0">Non visible</small>'}
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </body>

        </html>