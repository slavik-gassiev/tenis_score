

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Match score</title>
</head>
<body style="position: relative;height: 100vh;">


<div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);">

    <table border="1">
        <thead>
        <tr>
            <th>Players</th>
            <th>Score</th>
            <th>Deuce</th>
            <th>Game</th>
            <th>Tie-break</th>
            <th>Set</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>${p1name.name}
                <hr>
                ${p2name.name}</th>

            <td>${p1score}
                <hr>
                ${p2score}</td>

            <td>${p1deuce}
                <hr>
                ${p2deuce}</td>

            <td>${p1game}
                <hr>
                ${p2game}</td>

            <td>${p1TieBreak}
                <hr>
                ${p2TieBreak}</td>

            <td>${p1set}
                <hr>
                ${p2set}</td>

        </tr>
        </tbody>
    </table>

    <div style="margin-top: 2vh;">
        <form action="match-score?uuid=${uuid}" method="post" name="scoredform">
            <button  name ="point_winner" value="p1">${p1name.name} scored!</button>
            <button name="point_winner" value="p2">${p2name.name} scored!</button>
        </form>
    </div>
</div>



</body>
</html>





