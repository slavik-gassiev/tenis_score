

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Победил игрок ${winner}</title>
</head>
<body style="position: relative;height: 100vh;">


<div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);">

    <table>
        <tr>
            <td></td>
            <th>${p1name}</th>
            <th>${p2name}</th>
        </tr>
        <tr>
            <td>Score</td>
            <td>${p1score}</td>
            <td>${p2score}</td>
        </tr>
        <tr>
            <td>Deuce</td>
            <td>${p1deuce}</td>
            <td>${p2deuce}</td>
        </tr>
        <tr>
            <td>Game</td>
            <td>${p1game}</td>
            <td>${p2game}</td>
        </tr>
        <tr>
            <td>Tir-break</td>
            <td>${p1TieBreak}</td>
            <td>${p1TieBreak}</td>
        </tr>
        <tr>
            <td>Set</td>
            <td>${p1set}</td>
            <td>${p2set}</td>
        </tr>
    </table>

</div>
<footer>
    <a href="http://localhost:8080/tenis_score_war_exploded/new">Начать новый матч</a>
    <a href="http://localhost:8080/tenis_score_war_exploded/all">Посмотреть все матчи</a>
</footer>
</body>
</html>





