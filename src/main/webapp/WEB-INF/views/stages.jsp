<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Stages 2025</title>
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
                }

                .wrapper {
                    max-width: 1200px;
                    margin: 48px auto;
                    padding: 0 24px;
                }

                h1 {
                    margin: 0 0 12px;
                    font-size: 32px;
                    letter-spacing: 0.5px;
                }

                p.subtitle {
                    margin: 0 0 24px;
                    color: var(--muted);
                }

                .card {
                    background: var(--card);
                    border: 1px solid var(--border);
                    border-radius: 18px;
                    box-shadow: 0 22px 70px rgba(0, 0, 0, 0.4);
                    overflow: hidden;
                    backdrop-filter: blur(8px);
                }

                .table-wrap {
                    width: 100%;
                    overflow: hidden;
                    border-radius: 18px;
                    border: 1px solid var(--border);
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    color: var(--text);
                }

                thead {
                    background: linear-gradient(90deg, var(--accent) 0%, var(--accent-2) 100%);
                    text-transform: uppercase;
                    letter-spacing: 0.08em;
                    font-size: 13px;
                }

                th,
                td {
                    padding: 14px 16px;
                    text-align: left;
                    border-bottom: 1px solid var(--border);
                }

                th {
                    color: #0b1220;
                    font-weight: 800;
                    user-select: none;
                }

                th.sortable {
                    cursor: pointer;
                    transition: transform 120ms ease, background 120ms ease;
                }

                th.sortable:hover {
                    transform: translateY(-1px);
                    background: rgba(255, 255, 255, 0.08);
                }

                .th-content {
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    gap: 12px;
                }

                .sort-icon {
                    display: inline-flex;
                    align-items: center;
                    gap: 6px;
                    color: rgba(11, 18, 32, 0.85);
                    font-weight: 700;
                    font-size: 12px;
                    letter-spacing: 0.04em;
                }

                .sort-chip {
                    padding: 6px 10px;
                    background: rgba(11, 18, 32, 0.08);
                    border-radius: 999px;
                }

                tbody tr:nth-child(odd) {
                    background: rgba(255, 255, 255, 0.02);
                }

                tbody tr:hover {
                    background: rgba(6, 182, 212, 0.07);
                }

                td {
                    color: var(--text);
                    font-size: 15px;
                }

                td strong {
                    color: #fff;
                }

                .action-btn {
                    display: inline-block;
                    background: linear-gradient(135deg, var(--accent) 0%, var(--accent-2) 100%);
                    color: #0b1220;
                    padding: 8px 14px;
                    border-radius: 10px;
                    text-decoration: none;
                    font-weight: 700;
                    font-size: 0.95em;
                    box-shadow: 0 10px 25px rgba(6, 182, 212, 0.25);
                    transition: transform 120ms ease, box-shadow 120ms ease;
                }

                .action-btn:hover {
                    transform: translateY(-1px);
                    box-shadow: 0 14px 30px rgba(6, 182, 212, 0.35);
                }

                .empty {
                    text-align: center;
                    padding: 24px;
                    color: var(--muted);
                    font-style: italic;
                }

                @media (max-width: 900px) {

                    table,
                    thead,
                    tbody,
                    th,
                    td,
                    tr {
                        display: block;
                    }

                    thead {
                        display: none;
                    }

                    tbody tr {
                        border-bottom: 1px solid var(--border);
                        margin-bottom: 16px;
                        padding: 12px;
                        border-radius: 12px;
                        background: rgba(255, 255, 255, 0.02);
                    }

                    td {
                        padding: 10px 0;
                        border: none;
                    }

                    td::before {
                        content: attr(data-label);
                        display: block;
                        font-size: 12px;
                        text-transform: uppercase;
                        letter-spacing: 0.05em;
                        color: var(--muted);
                        margin-bottom: 2px;
                    }
                }
            </style>
        </head>

        <body>
            <div class="wrapper">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <div>
                        <h1>Stages 2025</h1>

                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${not empty user}">
                                <span style="margin-right: 15px;">Bonjour, <strong>${user.prenom}</strong></span>
                                <c:if test="${user.role == 'STUDENT'}">
                                    <a href="${pageContext.request.contextPath}/mvc/my-stages"
                                        style="color: #22d3ee; text-decoration: none; font-weight: bold; margin-right: 15px; border: 1px solid #22d3ee; padding: 5px 10px; border-radius: 5px;">
                                        Mes Stages
                                    </a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/mvc/auth/logout"
                                    style="color: #cbd5e1; text-decoration: none;">Déconnexion</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/mvc/auth/landing"
                                    style="background: #22d3ee; color: #0f172a; padding: 8px 16px; border-radius: 6px; text-decoration: none; font-weight: 600;">
                                    Se connecter
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <c:set var="currentSort" value="${empty sortField ? 'name' : sortField}" />
                <c:set var="currentDir" value="${empty sortDir ? 'asc' : sortDir}" />
                <c:set var="nameNextDir" value="${currentSort eq 'name' and currentDir eq 'asc' ? 'desc' : 'asc'}" />
                <c:set var="cityNextDir" value="${currentSort eq 'city' and currentDir eq 'asc' ? 'desc' : 'asc'}" />
                <c:set var="levelNextDir" value="${currentSort eq 'level' and currentDir eq 'asc' ? 'desc' : 'asc'}" />

                <div class="card">
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th class="sortable" onclick="window.location.href='?sort=name&dir=${nameNextDir}'">
                                        <span class="th-content">
                                            <span>Entreprise</span>
                                            <span class="sort-icon sort-chip">
                                                <c:choose>
                                                    <c:when test="${currentSort eq 'name' and currentDir eq 'asc'}">
                                                        &#9650;
                                                    </c:when>
                                                    <c:when test="${currentSort eq 'name' and currentDir eq 'desc'}">
                                                        &#9660;
                                                    </c:when>
                                                    <c:otherwise>
                                                        &#8645;
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </span>
                                    </th>
                                    <th class="sortable" onclick="window.location.href='?sort=city&dir=${cityNextDir}'">
                                        <span class="th-content">
                                            <span>Commune</span>
                                            <span class="sort-icon sort-chip">
                                                <c:choose>
                                                    <c:when test="${currentSort eq 'city' and currentDir eq 'asc'}">
                                                        &#9650;
                                                    </c:when>
                                                    <c:when test="${currentSort eq 'city' and currentDir eq 'desc'}">
                                                        &#9660;
                                                    </c:when>
                                                    <c:otherwise>
                                                        &#8645;
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </span>
                                    </th>
                                    <th>Code Postal</th>
                                    <th class="sortable"
                                        onclick="window.location.href='?sort=level&dir=${levelNextDir}'">
                                        <span class="th-content">
                                            <span>Niveaux</span>
                                            <span class="sort-icon sort-chip">
                                                <c:choose>
                                                    <c:when test="${currentSort eq 'level' and currentDir eq 'asc'}">
                                                        &#9650;
                                                    </c:when>
                                                    <c:when test="${currentSort eq 'level' and currentDir eq 'desc'}">
                                                        &#9660;
                                                    </c:when>
                                                    <c:otherwise>
                                                        &#8645;
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </span>
                                    </th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${empty stages}">
                                    <tr>
                                        <td class="empty" colspan="5">Aucune entreprise visible pour le moment.</td>
                                    </tr>
                                </c:if>
                                <c:forEach var="stage" items="${stages}">
                                    <tr>
                                        <td data-label="Entreprise"><strong>${stage.name}</strong></td>
                                        <td data-label="Commune">${stage.city}</td>
                                        <td data-label="Code Postal">${stage.zip}</td>
                                        <td data-label="Niveaux">${stage.formations}</td>
                                        <td data-label="Action">
                                            <a class="action-btn"
                                                href="${pageContext.request.contextPath}/mvc/company/${stage.name}">
                                                Voir fiche détaillée
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </body>

        </html>