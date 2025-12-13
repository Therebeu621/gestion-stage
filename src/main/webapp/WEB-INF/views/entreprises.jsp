<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Entreprises</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; cursor: pointer; }
        tr:hover { background-color: #f5f5f5; }
        a { text-decoration: none; color: #007bff; }
        a:hover { text-decoration: underline; }
    </style>
    <script>
        function sortTable(n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("myTable");
            switching = true;
            dir = "asc";
            while (switching) {
                switching = false;
                rows = table.rows;
                for (i = 1; i < (rows.length - 1); i++) {
                    shouldSwitch = false;
                    x = rows[i].getElementsByTagName("TD")[n];
                    y = rows[i + 1].getElementsByTagName("TD")[n];
                    if (dir == "asc") {
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir == "desc") {
                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                            shouldSwitch = true;
                            break;
                        }
                    }
                }
                if (shouldSwitch) {
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;
                    switchcount++;
                } else {
                    if (switchcount == 0 && dir == "asc") {
                        dir = "desc";
                        switching = true;
                    }
                }
            }
        }
    </script>
</head>
<body>
    <h1>Entreprises ayant accueilli des stagiaires en 2025</h1>
    
    <p><a href="${pageContext.request.contextPath}/mvc/">Retour à la liste des stages</a></p>

    <table id="myTable">
        <thead>
            <tr>
                <th onclick="sortTable(0)">Nom (Trier)</th>
                <th onclick="sortTable(1)">Commune (Trier)</th>
                <th onclick="sortTable(2)">Code Postal (Trier)</th>
                <th>Nombre de stagiaires</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entreprise" items="${entreprises}">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/mvc/entreprises/${entreprise.id}">
                            ${entreprise.nom}
                        </a>
                    </td>
                    <td>${entreprise.commune}</td>
                    <td>${entreprise.codePostal}</td>
                    <td>${entreprise.stages.size()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
