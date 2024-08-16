

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Match score</title>
</head>
<body>


<div>

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

    <div class="score-form">
        <form class="scoreform" action="match-score?uuid=${uuid}" method="post" name="scoredform">
            <button name = pscored value="1">${p1name} scored!</button>
            <button name="pscored" value="2">${p2name} scored!</button>
        </form>
    </div>
</div>
</body>
</html>