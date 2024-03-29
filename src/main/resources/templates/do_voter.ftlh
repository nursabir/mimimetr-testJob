<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voting Options</title>
    <style>
        body {
            margin: 0;
        }

        .cat {
            display: inline-block;
            margin: 10px;
            text-align: center;
            cursor: pointer;
        }

        .cat p {
            margin: 5px 0;
        }

        .cat img {
            max-width: 150px;
            max-height: 150px;
        }

        .selected {
            background-color: lightblue;
        }
    </style>
</head>
<body>
<div id="top10Container"></div>

<div id="catsContainer"></div>
<div id="catsContainer">
    <p>Голосование! Кто мимимшнее?</p>
</div>
<div id="emailContainer" style="display: none;">
    <label for="email">Введите ваш email:</label>
    <input type="email" id="email" name="email">
</div>
<button id="submitVotes" style="display: none;">Отправить результаты</button>

<script th:inline="javascript">
    var catsJson = /*[[${cats_form}]]*/ [];
    var catsJsonToBack = catsJson.slice(0);
    console.log(catsJsonToBack);
    console.log(catsJson);

    var currentIndex = 0;
    var index2 = 1;
    var indexesForVote = [];
    for (var i = 0; i < catsJson.length; i++) {
        indexesForVote.push(i);
    }

    function sap() {
        if (currentIndex >= catsJson.length || index2 >= catsJson.length) {
            document.getElementById("emailContainer").style.display = "block";
            document.getElementById("submitVotes").style.display = "block";
            document.getElementById("catsContainer").innerHTML = "";
            return;
        }
        cat1 = catsJson[currentIndex];
        cat2 = catsJson[index2];
        displayCatPairs(cat1, cat2)
        console.log(cat1.catName)
        console.log(cat2.catName)

    }

    function displayCatPairs(cat1, cat2) {
        var container = document.getElementById("catsContainer");
        container.innerHTML = "";

        var catPairDiv = document.createElement("div");
        catPairDiv.classList.add("cat");

        var cat1Image = document.createElement("img");
        cat1Image.src = "/" + cat1.catPhotoPath;
        cat1Image.alt = "Cat Photo";
        cat1Image.setAttribute("data-catid", cat1.catId);


        var cat2Image = document.createElement("img");
        cat2Image.src = "/" + cat2.catPhotoPath;
        cat2Image.alt = "Cat Photo";
        cat2Image.setAttribute("data-catid", cat2.catId);

        cat1Image.addEventListener("click", function (event) {
            var clickedCatId = event.target.getAttribute("data-catid");
            if (clickedCatId) {
                console.log(clickedCatId);
                vote(clickedCatId);
                this.classList.add("selected");

                cat2Image.classList.remove("selected");
                if (indexesForVote.length === 0) {

                }
                if (index2 !== indexesForVote.length) {
                    sap();
                    index2++;
                } else {
                    indexesForVote.shift();
                    currentIndex = indexesForVote[0];
                    index2 = currentIndex + 1;
                    sap();
                }
            }
        });
        cat2Image.addEventListener("click", function (event) {
            var clickedCatId = event.target.getAttribute("data-catid");
            if (clickedCatId) {
                console.log(clickedCatId);
                vote(clickedCatId);
                this.classList.add("selected");
                cat1Image.classList.remove("selected");
                if (index2 !== indexesForVote.length) {
                    sap();
                    index2++;
                } else {
                    indexesForVote.shift();
                    currentIndex = indexesForVote[0];
                    index2 = currentIndex + 1;
                    sap();
                }
            }
        });

        catPairDiv.appendChild(document.createTextNode(cat1.catName));
        catPairDiv.appendChild(cat1Image);

        catPairDiv.appendChild(document.createTextNode(cat2.catName));
        catPairDiv.appendChild(cat2Image);

        container.appendChild(catPairDiv);

        container.appendChild(catPairDiv);
    }


    function vote(catId) {
        console.log("Выбрали кота")
        console.log("Выбрали кота")
        console.log("Выбрали кота")
        console.log("Выбрали кота")
        console.log("Выбрали кота")
        // Находим выбранного кота и увеличиваем количество его голосов
        catsJsonToBack.forEach(function (cat) {
            console.log("Вот идшники котов")
            console.log(cat.catId);
            console.log(catId);
            if (cat.catId == catId) {
                console.log("коттт")
                cat.numberOfVotes++;
            }
        });
    }

    function top10() {
        var top10xhr = new XMLHttpRequest();
        top10xhr.open("GET", "/top10_cats", true);
        top10xhr.onreadystatechange = function () {
            if (top10xhr.readyState === XMLHttpRequest.DONE) {
                if (top10xhr.status === 200) {
                    var top10cats = /*[[${cats_form}]]*/ [];
                    displayTop10(top10cats);
                    console.log("Топ-10 котов:", top10cats);
                } else {
                    console.error("Ошибка получения топ-10 котов");
                }
            }
        };
        top10xhr.send();
    }

    function displayTop10(cats) {
        var container = document.getElementById("top10Container");

        container.innerHTML = "";

        var title = document.createElement("h2");
        title.textContent = "Топ-10 котов";
        container.appendChild(title);

        cats.forEach(function (cat) {
            var catDiv = document.createElement("div");
            catDiv.classList.add("cat");

            var catImage = document.createElement("img");
            catImage.src = "/" + cat.catPhotoPath;
            catImage.alt = "Cat Photo";
            catImage.style.maxWidth = "150px";
            catImage.style.maxHeight = "150px";

            var namePara = document.createElement("p");
            namePara.textContent = "Имя: " + cat.catName;
            var votesPara = document.createElement("p");
            votesPara.textContent = "Количество голосов: " + cat.numberOfVotes;

            catDiv.appendChild(catImage);
            catDiv.appendChild(namePara);
            catDiv.appendChild(votesPara);

            container.appendChild(catDiv);
        });
    }

    document
        .getElementById("submitVotes").onclick = function () {
        var email = document.getElementById("email").value;
        var dataToSend = catsJsonToBack;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/register_vote/ofUser/" + email, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("Голоса успешно отправлены на сервер!");

                    top10();


                } else {
                    var errorContainer = document.getElementById("errorContainer");
                    errorContainer.innerText = "Ошибка сохранения голосов. Пожалуйста, попробуйте еще раз.";
                    errorContainer.style.display = "block";
                }
            }
        };
        xhr.send(JSON.stringify(dataToSend));
    }


    sap();

    /*]]>*/
</script>
</body>
</html>