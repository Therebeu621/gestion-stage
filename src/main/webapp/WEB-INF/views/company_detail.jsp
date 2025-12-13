<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Détails Entreprise - ${companyName}</title>
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

                * {
                    box-sizing: border-box;
                }

                body {
                    font-family: "Segoe UI", Helvetica, Arial, sans-serif;
                    margin: 0;
                    min-height: 100vh;
                    background: radial-gradient(circle at 20% 20%, rgba(14, 165, 233, 0.12), transparent 35%),
                        radial-gradient(circle at 80% 10%, rgba(6, 182, 212, 0.12), transparent 30%),
                        linear-gradient(135deg, #0b1220 0%, #0f172a 50%, #0b1220 100%);
                    color: var(--text);
                    display: flex;
                    justify-content: center;
                    padding: 40px 20px;
                }

                .container {
                    width: 100%;
                    max-width: 900px;
                    background: var(--card);
                    padding: 40px;
                    border-radius: 18px;
                    box-shadow: 0 22px 70px rgba(0, 0, 0, 0.4);
                    border: 1px solid var(--border);
                    backdrop-filter: blur(8px);
                }

                header {
                    border-bottom: 1px solid var(--border);
                    padding-bottom: 24px;
                    margin-bottom: 32px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }

                h1 {
                    color: #fff;
                    margin: 0;
                    font-size: 2rem;
                    letter-spacing: -0.02em;
                }

                .back-link {
                    text-decoration: none;
                    color: var(--accent);
                    font-weight: 600;
                    padding: 8px 16px;
                    border-radius: 8px;
                    background: rgba(14, 165, 233, 0.1);
                    transition: all 0.2s ease;
                }

                .back-link:hover {
                    background: rgba(14, 165, 233, 0.2);
                    color: #38bdf8;
                }

                .info-card {
                    background: rgba(255, 255, 255, 0.03);
                    padding: 24px;
                    border-radius: 12px;
                    margin-bottom: 32px;
                    border: 1px solid var(--border);
                }

                .info-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                    gap: 24px;
                }

                .info-item label {
                    display: block;
                    color: var(--muted);
                    font-size: 0.75rem;
                    margin-bottom: 6px;
                    text-transform: uppercase;
                    letter-spacing: 0.08em;
                    font-weight: 700;
                }

                .info-item span {
                    color: #fff;
                    font-weight: 600;
                    font-size: 1.1rem;
                }

                h2 {
                    color: var(--text);
                    margin-bottom: 20px;
                    font-size: 1.4rem;
                    border-left: 4px solid var(--accent);
                    padding-left: 12px;
                }

                .table-wrap {
                    width: 100%;
                    overflow: hidden;
                    border-radius: 12px;
                    border: 1px solid var(--border);
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    color: var(--text);
                }

                thead {
                    background: linear-gradient(90deg, var(--accent) 0%, var(--accent-2) 100%);
                }

                th {
                    padding: 14px 16px;
                    text-align: left;
                    color: #0b1220;
                    font-weight: 800;
                    font-size: 0.85rem;
                    text-transform: uppercase;
                    letter-spacing: 0.05em;
                }

                td {
                    padding: 14px 16px;
                    border-bottom: 1px solid var(--border);
                    font-size: 0.95rem;
                }

                tbody tr:last-child td {
                    border-bottom: none;
                }

                tbody tr:hover {
                    background: rgba(255, 255, 255, 0.03);
                }
            </style>
        </head>

        <body>
            <div class="container">
                <header>
                    <h1>${companyName}</h1>
                    <a href="${pageContext.request.contextPath}/mvc/" class="back-link">
                        &#8592; Retour à la liste
                    </a>
                </header>

                <c:if test="${not empty entreprise}">
                    <div class="info-card">
                        <div class="info-grid">
                            <div class="info-item">
                                <label>Adresse</label>
                                <span>${entreprise.adresse}</span>
                            </div>
                            <div class="info-item">
                                <label>Ville</label>
                                <span>${entreprise.commune}</span>
                            </div>
                            <div class="info-item">
                                <label>Code Postal</label>
                                <span>${entreprise.codePostal}</span>
                            </div>
                            <div class="info-item">
                                <label>Site Web</label>
                                <span><a href="http://${entreprise.siteWeb}" target="_blank"
                                        style="color: #fff; text-decoration: underline;">${entreprise.siteWeb}</a></span>
                            </div>
                            <div class="info-item">
                                <label>Contact</label>
                                <span>${entreprise.contact}</span>
                            </div>
                        </div>
                    </div>
                </c:if>

                <h2>Historique des stagiaires</h2>
                <div class="table-wrap">
                    <table>
                        <thead>
                            <tr>
                                <th>Étudiant</th>
                                <th>Formation</th>
                                <th>Période</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="stage" items="${entreprise.stages}">
                                <tr>
                                    <td>
                                        <strong style="color: #fff;">${stage.prenomEtudiant}
                                            ${stage.nomEtudiant}</strong>
                                    </td>
                                    <td>
                                        <span
                                            style="background: rgba(14, 165, 233, 0.15); color: #38bdf8; padding: 4px 10px; border-radius: 99px; font-size: 0.85em; font-weight: 600;">
                                            ${stage.formation}
                                        </span>
                                    </td>
                                    <td style="color: var(--muted); font-size: 0.9em;">
                                        Du ${stage.dateDebut} au ${stage.dateFin}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </body>

        </html>