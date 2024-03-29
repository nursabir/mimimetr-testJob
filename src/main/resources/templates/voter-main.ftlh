<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voting Options</title>
</head>
<body>
<h1>Выберите голосование</h1>
<ul>
    <li th:each="entry : ${votingTitlesMap}">

        <a th:href="@{'/voter/' + ${entry.key}}"><span th:text="${entry.value}"></span></a>
    </li>
</ul>
</body>
</html>
