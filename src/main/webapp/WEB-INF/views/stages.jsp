<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Stages 2025</title>
    <style>
        body {
            font-family: "Segoe UI", Helvetica, Arial, sans-serif;
            margin: 0;
            background: linear-gradient(135deg, #0f172a 0%, #1e293b 40%, #0f172a 100%);
            color: #e2e8f0;
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
            color: #cbd5e1;
        }

        .card {
            background: rgba(15, 23, 42, 0.75);
            border: 1px solid #1f2937;
            border-radius: 16px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.35);
            overflow: hidden;
            backdrop-filter: blur(8px);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead {
            background: linear-gradient(90deg, #0ea5e9 0%, #22d3ee 100%);
            color: #0b1220;
            text-transform: uppercase;
            letter-spacing: 0.08em;
            font-size: 13px;
        }

        th, td {
            padding: 12px 14px;
            text-align: left;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }

        tbody tr:nth-child(odd) {
            background: rgba(255, 255, 255, 0.02);
        }

        tbody tr:hover {
            background: rgba(34, 211, 238, 0.08);
        }

        .empty {
            text-align: center;
            padding: 24px;
            color: #94a3b8;
            font-style: italic;
        }

        @media (max-width: 900px) {
            table, thead, tbody, th, td, tr {
                display: block;
            }

            thead {
                display: none;
            }

            tbody tr {
                border-bottom: 1px solid rgba(255, 255, 255, 0.08);
                margin-bottom: 16px;
                padding: 12px;
            }

            td {
                padding: 8px 0;
                border: none;
            }

            td::before {
                content: attr(data-label);
                display: block;
                font-size: 12px;
                text-transform: uppercase;
                letter-spacing: 0.05em;
                color: #94a3b8;
                margin-bottom: 2px;
            }
        }
    </style>
</head>
<body>
<div class="wrapper">
    <h1>Stages 2025</h1>
    <p class="subtitle">Données chargées depuis le fichier CSV fourni.</p>

    <div class="card">
        <table>
            <thead>
            <tr>
                <c:forEach var="header" items="${headers}">
                    <th>${header}</th>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty stages}">
                <tr>
                    <td class="empty" colspan="10">Aucune donnée à afficher.</td>
                </tr>
            </c:if>
            <c:forEach var="stage" items="${stages}">
                <tr>
                    <td data-label="${headers[0]}">${stage.nomEtudiant}</td>
                    <td data-label="${headers[1]}">${stage.prenomEtudiant}</td>
                    <td data-label="${headers[2]}">${stage.mailUniversitaire}</td>
                    <td data-label="${headers[3]}">${stage.dateDebut}</td>
                    <td data-label="${headers[4]}">${stage.dateFin}</td>
                    <td data-label="${headers[5]}">${stage.formation}</td>
                    <td data-label="${headers[6]}">${stage.prenomEnseignantReferent}</td>
                    <td data-label="${headers[7]}">${stage.nomEtablissementAccueil}</td>
                    <td data-label="${headers[8]}">${stage.communeEtablissement}</td>
                    <td data-label="${headers[9]}">${stage.codePostal}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
