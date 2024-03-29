<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Главная страница администратора</title>
    <style>
        .voting {
            margin-bottom: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            cursor: pointer;
        }

        .voting h3 {
            margin: 0;
            cursor: pointer;
        }

        .voteDetails {
            margin-top: 10px;
            display: none;
        }

        .voteDetails ul {
            list-style-type: none;
            padding-left: 0;
        }

        .cat {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .cat img {
            max-width: 150px;
            max-height: 150px;
            margin-left: 20px;
        }
    </style>
</head>
<body>
<h1>Начать голосование</h1>
<p>Для формирования голосования нажмите кнопку ниже:</p>
<form>
    <button type="button" onclick="showVotingForm()">Сформировать голосование</button>
</form>

<div id="votingFormContainer" style="display: none;">
    <h2>Форма голосования</h2>
    <form id="votingForm" enctype="multipart/form-data">
        <label for="votingTitle">Название голосования:</label><br>
        <input type="text" id="votingTitle" name="votingTitle"><br><br>

        <div id="cats">
            <div class="cat">
                <label for="cats[0].catName">Имя кота:</label>
                <input type="text" name="cats[0].catName">
                <label for="cats[0].catPhoto">Фотография кота:</label>
                <input type="file" name="cats[0].catPhoto">
            </div>
        </div>
        <button type="button" onclick="addCat()">Добавить кота</button><br><br>

        <button type="button" onclick="saveAndStartVoting()">Сохранить и начать голосование</button>
    </form>
</div>

<h2>Текущие голосования:</h2>
<div id="votingList">
    <div th:each="voteForm : ${voteForms}" class="voting" onclick="showVoteDetails(this)">
        <h3 th:text="${voteForm.votingTitle}"></h3>
        <span>Статус: <span th:text="${voteForm.voteStatus}"></span></span>

        <div class="voteDetails">
            <ul>
                <li th:each="cat : ${voteForm.cats}">
                    <div class="cat">
                        <p>Этого кота зовут <span th:text="${cat.catName}"></span>. За него проголосовали <span th:text="${cat.numberOfVotes}"></span> раз(а).</p>
                        <img th:src="@{'/' + ${cat.catPhotoPath}}" alt="Photo">

                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>

<script>
    function completeVoting(votingId) {
        console.log("Начинается омтена голосвание");
        var xhr = new XMLHttpRequest();
        xhr.open("PATCH", "/complete-voting/" + votingId, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("Голосование успешно завершено!");
                } else {
                    alert("Произошла ошибка при завершении голосования.");
                }
            }
        };
        xhr.send();
    }
    function addCat() {
        var catsDiv = document.getElementById("cats");
        var index = catsDiv.childElementCount;
        var newCatDiv = document.createElement("div");
        newCatDiv.classList.add("cat");
        newCatDiv.innerHTML = '<label for="cats[' + index + '].catName">Имя кота:</label> <input type="text" name="cats[' + index + '].catName"> <label for="cats[' + index + '].catPhoto">Фотография кота:</label> <input type="file" name="cats[' + index + '].catPhoto">';
        catsDiv.appendChild(newCatDiv);
    }


    function showVotingForm() {
        document.getElementById('votingFormContainer').style.display = 'block';
    }

    function showVoteDetails(voteDiv) {
        var detailsDiv = voteDiv.getElementsByClassName('voteDetails')[0];
        detailsDiv.style.display = (detailsDiv.style.display === 'none' || !detailsDiv.style.display) ? 'block' : 'none';
    }

    function saveAndStartVoting() {
        var form = document.getElementById('votingForm');
        var formData = new FormData(form);

        for (var pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }

        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/save-voting', true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log('Данные успешно отправлены!');
            } else {
                console.error('Произошла ошибка при отправке данных.');
            }
        };
        xhr.send(formData);
    }

</script>
</body>
</html>
