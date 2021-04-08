dificuldadeJogo = null;
objDimensoesTabela = null;
jsonDimensoesTabela = null;

document.getElementById("form-dificuldade-jogo").addEventListener("submit",
        function (e) {
            e.preventDefault();
            var botoesRadio = document.getElementsByName('dificuldade');
            for (var i = 0; i < botoesRadio.length; i++) {
                if (botoesRadio[i].checked) {
                    dificuldadeJogo = botoesRadio[i].value;
                    break;
                }
            }

            if (dificuldadeJogo === "iniciante") {
                objDimensoesTabela = {
                    numLinhas: 9,
                    numColunas: 9
                };
            } else if (dificuldadeJogo === "intermediario") {
                objDimensoesTabela = {
                    numLinhas: 16,
                    numColunas: 16
                };
            } else if (dificuldadeJogo === "avancado") {
                objDimensoesTabela = {
                    numLinhas: 16,
                    numColunas: 40
                };
            }

            jsonDimensoesTabela = JSON.stringify(objDimensoesTabela);


            var xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4) {
                    sessionStorage.setItem("jogo",xhttp.responseText);
                    window.location.href = "jogo.html";
                }
            };
            xhttp.open("POST", "resources/jogo");
            xhttp.setRequestHeader("Content-Type", "application/json");
            xhttp.send(jsonDimensoesTabela);

        }
);

