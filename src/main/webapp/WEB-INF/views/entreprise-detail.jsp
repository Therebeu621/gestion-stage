<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détail Entreprise - ${entreprise.nom}</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .card { border: 1px solid #ddd; padding: 20px; border-radius: 8px; max-width: 600px; margin-bottom: 20px; }
        .label { font-weight: bold; }
        table { border-collapse: collapse; width: 100%; margin-top: 10px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        a { text-decoration: none; color: #007bff; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/mvc/entreprises">Retour à la liste des entreprises</a></p>
    
    <h1>${entreprise.nom}</h1>

    <div class="card">
        <h3>Informations Contact</h3>
        <p><span class="label">Adresse :</span> ${entreprise.adresse}, ${entreprise.codePostal} ${entreprise.commune}</p>
        <p><span class="label">Site Web :</span> <a href="http://${entreprise.siteWeb}" target="_blank">${entreprise.siteWeb}</a></p>
        <p><span class="label">Contact :</span> <a href="mailto:${entreprise.contact}">${entreprise.contact}</a></p>
    </div>

    <h3>Stagiaires accueillis (Accord donné)</h3>
    <c:if test="${empty entreprise.stages}">
        <p>Aucun stagiaire n'a donné son accord pour apparaître ici.</p>
    </c:if>
    <c:if test="${not empty entreprise.stages}">
        <table>
            <thead>
                <tr>
                    <th>Etudiant</th>
                    <th>Formation</th>
                    <th>Période</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="stage" items="${entreprise.stages}">
                    <tr>
                        <td>${stage.prenomEtudiant} ${stage.nomEtudiant}</td>
                        <td>${stage.formation}</td>
                        <td>Du ${stage.dateDebut} au ${stage.dateFin}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

</body>
</html>
