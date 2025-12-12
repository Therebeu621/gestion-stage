<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Détails Entreprise - ${companyName}</title>
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

                header {
                    border-bottom: 2px solid #edf2f7;
                    padding-bottom: 20px;
                    margin-bottom: 30px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }

                h1 {
                    color: #2d3748;
                    margin: 0;
                    font-size: 2rem;
                }

                .back-link {
                    text-decoration: none;
                    color: #4facfe;
                    font-weight: 600;
                }

                .info-card {
                    background: #f8fafc;
                    padding: 20px;
                    border-radius: 8px;
                    margin-bottom: 30px;
                    border: 1px solid #e2e8f0;
                }

                .info-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                    gap: 20px;
                }

                .info-item label {
                    display: block;
                    color: #718096;
                    font-size: 0.875rem;
                    margin-bottom: 5px;
                    text-transform: uppercase;
                    letter-spacing: 0.05em;
                }

                .info-item span {
                    color: #2d3748;
                    font-weight: 600;
                    font-size: 1.125rem;
                }

                h2 {
                    color: #4a5568;
                    margin-bottom: 20px;
                    font-size: 1.5rem;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    background: white;
                    border-radius: 8px;
                    overflow: hidden;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
                }

                th {
                    background: #edf2f7;
                    color: #4a5568;
                    font-weight: 600;
                    padding: 15px;
                    text-align: left;
                    border-bottom: 2px solid #e2e8f0;
                }

                td {
                    padding: 15px;
                    border-bottom: 1px solid #edf2f7;
                    color: #2d3748;
                }

                tr:last-child td {
                    border-bottom: none;
                }

                tr:hover {
                    background-color: #f7fafc;
                }
            </style>
        </head>

        <body>
            <div class="container">
                <header>
                    <h1>${companyName}</h1>
                    <a href="${pageContext.request.contextPath}/mvc/" class="back-link">← Retour à la liste</a>
                </header>

                <c:if test="${not empty companyInfo}">
                    <div class="info-card">
                        <div class="info-grid">
                            <div class="info-item">
                                <label>Ville</label>
                                <span>${companyInfo.communeEtablissement}</span>
                            </div>
                            <div class="info-item">
                                <label>Code Postal</label>
                                <span>${companyInfo.codePostal}</span>
                            </div>
                        </div>
                    </div>
                </c:if>

                <h2>Historique des stagiaires</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Étudiant</th>
                            <th>Formation</th>
                            <th>Période</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="stage" items="${stages}">
                            <tr>
                                <td>${stage.prenomEtudiant} ${stage.nomEtudiant}</td>
                                <td>${stage.formation}</td>
                                <td>Du ${stage.dateDebut} au ${stage.dateFin}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </body>

        </html>