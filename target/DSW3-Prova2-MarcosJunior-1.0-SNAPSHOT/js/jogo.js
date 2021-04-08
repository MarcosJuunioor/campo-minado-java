jogo = null;

document.onload = criarTabuleiro();
function criarTabuleiro() {
    jogo = JSON.parse(sessionStorage.getItem("jogo"));
    /*
     * {
     *      fimDoJogo: boolean,
     *      tabuleiro:{
     *          bombasRestantes: int,
     *          celulas: array de celulas,
     *          dt: dimensoes,
     *          numBombas: int
     *      }
     * }
     */
    window.oncontextmenu = function () {
        return false;
    };

    var tabuleiro = document.getElementById("tabuleiro");

    for (var i = 1; i <= jogo.tabuleiro.dt.numLinhas; i++) {

        var linha = document.createElement("tr");
        for (var j = 1; j <= jogo.tabuleiro.dt.numColunas; j++) {
            var celula = document.createElement("td");
            var enderecoCelula = "l" + i + "c" + j;
            celula.setAttribute("id", enderecoCelula);
            //o name possui o conteúdo da célula (número, bomba ou vazia)
            celula.setAttribute("name", jogo.tabuleiro.celulas[enderecoCelula].conteudoCelula);
            celula.setAttribute("class", "celulas");
            celula.setAttribute("style", "background:grey");

            $(celula).mousedown(function (event) {
                event.preventDefault();
                switch (event.which) {
                    case 1:
                        revelarCelula(event.target.id);
                        break;
                    case 3:
                        colocarOuRetirarBandeira(event.target.id);
                        break;
                }
            });


            linha.appendChild(celula);
        }
        tabuleiro.appendChild(linha);
    }

}

function revelarCelula(id) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4) {
            jogo = JSON.parse(xhttp.responseText);
        }
    };
    xhttp.open("PUT", "resources/jogo/" + id, false);
    xhttp.send();

    //verificar se o jogo acabou
    if (jogo.fimDoJogo) {
        for (var i = 1; i <= jogo.tabuleiro.dt.numLinhas; i++) {
            for (var j = 1; j <= jogo.tabuleiro.dt.numColunas; j++) {
                var enderecoCelula = "l" + i + "c" + j;
                var celula = document.getElementById(enderecoCelula);
                var conteudoCelula = celula.getAttribute("name");
                if (conteudoCelula === "BOMBA" && !jogo.tabuleiro.celulas[enderecoCelula].bandeira) {
                    celula.setAttribute("style", "background:#BEBEBE");
                    var imgBomba = document.createElement("img");
                    imgBomba.setAttribute("src", "imagens/mine.png");
                    imgBomba.setAttribute("class", "img-bomba");
                    imgBomba.setAttribute("style", "width: 22px");
                    imgBomba.setAttribute("style", "height: 22px");
                    celula.appendChild(imgBomba);
                }

            }
        }
        document.getElementById("mensagem").innerHTML = "Você perdeu!";
    } else {
        for (var i = 1; i <= jogo.tabuleiro.dt.numLinhas; i++) {
            for (var j = 1; j <= jogo.tabuleiro.dt.numColunas; j++) {
                var enderecoCelula = "l" + i + "c" + j;

                if (jogo.tabuleiro.celulas[enderecoCelula].revelada && !jogo.tabuleiro.celulas[enderecoCelula].bandeira) {
                    var celula = document.getElementById(enderecoCelula);
                    celula.setAttribute("style", "background:#BEBEBE");
                    var conteudoCelula = celula.getAttribute("name");
                    if (conteudoCelula === "BOMBA") {
                        var imgBomba = document.createElement("img");
                        imgBomba.setAttribute("src", "imagens/mine.png");
                        imgBomba.setAttribute("class", "img-bomba");
                        imgBomba.setAttribute("style", "width: 22px");
                        imgBomba.setAttribute("style", "height: 22px");
                        celula.appendChild(imgBomba);
                    } else if (getNumero(conteudoCelula) !== 0) {
                        celula.innerHTML = getNumero(conteudoCelula);
                    }
                }
            }
        }
    }
}

function colocarOuRetirarBandeira(id) {
    
    if (jogo.tabuleiro.celulas[id.replace('img-flag-','')].bandeira) {
        id = id.replace('img-flag-','');
        var xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4) {
                jogo = JSON.parse(xhttp.responseText);
                document.getElementById("img-flag-"+id).remove();
            }
        };
        xhttp.open("DELETE", "resources/bandeira/" + id, false);
        xhttp.send();
    } else {
        var celula = document.getElementById(id);
        var imgFlag = document.createElement("img");
        imgFlag.setAttribute("src", "imagens/flag.png");
        imgFlag.setAttribute("class", "img-flag");
        imgFlag.setAttribute("id", "img-flag-"+id);
        imgFlag.setAttribute("style", "width: 22px");
        imgFlag.setAttribute("style", "height: 22px");
        celula.appendChild(imgFlag);

        var xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4) {
                jogo = JSON.parse(xhttp.responseText);
            }
        };
        xhttp.open("PUT", "resources/bandeira/" + id, false);
        xhttp.send();
    }
}

function getNumero(conteudoCelula) {
    switch (conteudoCelula) {
        case "UM":
            return 1;
        case "DOIS":
            return 2;
        case "TRES":
            return 3;
        case "QUATRO":
            return 4;
        case "CINCO":
            return 5;
        case "SEIS":
            return 6;
        case "SETE":
            return 7;
        case "OITO":
            return 8;
        default:
            return 0;
    }

}