<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Musikplayer</title>
        <link rel="icon" href="logo.png" type="image/x-icon">
        <style>
            html, body {height: 100%; overflow: auto; position: relative; color: white; background-image: url("login.png");}
            hr{color: white;clear:both;margin-top:5%;}
            h1{
                float:left;
                margin-right: 2%;
            }
            #logo{
                float:left;
                width:50px;
                height:50px;
            }
            #wrapper { bottom: 0; height: 100%; margin: 0 auto; position: relative; top: 0; width: 100%;}
            td{text-align:center; padding-right: 5px;}
            tr{border: 1px solid grey; opacity:0.8;}
            th {padding-bottom: 20px;}
            div#button {
                background:grey;
                text-align: center;
            }
            hr{
                float:next;
            }
            #search {float:right; margin-top: 1%; margin-right:5%;}
            #logout{float:right; margin-top: 1%; margin-right: 1%;}
            #textarea{float:right; margin-top: 1%; margin-right: 1%;}
            #player{margin-right: 10%; width: 80%}
            #empfehlungForm{}
            #playlist{width:100%;}
            #empfehlungen {border: 3px solid grey; width: 80%; margin-left: 10%;}
            #informationen {position: absolute; border: 3px solid grey; width: 80%; margin-left: 10%; margin-top: 1%;/*margin-bottom: 10%;*/}
            .abschnitt {float: left; width: 60%; height: 30%; /*overflow: scroll*/}
            .zwei  { width: 40%; height: 30%; margin-top: 3%; overflow: hidden;}
            .clear { clear: both; }
        </style>
        <script>
            function buildEmpfehlungenRow(rowid) {
                var xhttp = new XMLHttpRequest();
                var buttons = "<td><div id=\"button\" onclick=\"buildEmpfehlungenRow(" + rowid + ")\">X</div></td>";
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById(rowid).innerHTML = xhttp.responseText + buttons + "</tr>";
                    }
                }
                xhttp.open("POST", "home", true);
                xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xhttp.send("action=suggest&rowid="+rowid);
            }

            function buildTablePlaylist() {
                var tabelle = "<table><th>Inhalt der Playlisten</th>";
                for (var i = 0; i < 15; i++) {
                    tabelle = tabelle + "<tr id=p\"" + i + "\" name=\"play\" value=\"play\" onclick=\"document.location='HomeServlet'\" method=\"post\">";
                    var titel = "<td>Titel</td>";
                    var interpret = "<td>Interpret</td>";
                    var dauer = "<td>Dauer</td>";
                    var genre = "<td>Genre</td>";
                    var bewertung = "<td>Bewertung</td>";
                    var buttons = "<td><button type=\"submit\" name=\"play\" value=\"1\">></button><button type=\"submit\" name=\"loeschen\" value=\"loeschen\">X</button></td>";
                    tabelle = tabelle + titel + interpret + dauer + genre + bewertung + buttons + "</tr>";
                }
                tabelle = tabelle + "</table>";
                document.getElementById("playlist").innerHTML = tabelle;
            }

            function loadSongInfo(songname) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById("playerinfo").innerHTML = xhttp.responseText;
                    }
                }
                xhttp.open("POST", "home", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("action=info&songname=" + songname);
            }
            function playSong(songname) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById("audio_with_controls").src = xhttp.responseText;
                        document.getElementById("audio_with_controls").play();
                    }
                }
                xhttp.open("POST", "home", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("action=play&songname=" + songname);
            }
            function playSuggest(rowid) {
                var songname = document.getElementById("songname" + rowid).innerHTML;
                loadSongInfo(songname);
                playSong(songname);
                buildEmpfehlungenRow(rowid);
            }
        </script>
    </head>
    <body >
        <div id="wrapper">
            <header>
            <h1>SLAPMusic!</h1>
            <image id="logo" src="logo.png" alt="Logo not found!"/>
            <button id="logout" onclick="document.location='index.html'">Logout</button>
            <div id="textarea">${username}</div>
            <input id="search">
            </header>
            <hr>
            <form id="empfehlungForm" action="home" method="post" autocomplete="off">
                <table id="empfehlungen"><tr><th>Empfehlungen</th></tr>
                    <tr>
                        <th>Titel</th>
                        <th>Interpret</th>
                        <th>Dauer</th>
                        <th>Genre</th>
                        <th>durchschnittliche Bewertung</th>                    
                    </tr>
                    <tr id=0 onclick="playSuggest(0)">
                    <script type="text/javascript">buildEmpfehlungenRow(0);</script>
                    </tr>   
                    <tr id=1 onclick="playSuggest(1)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(1);</script>
                    </tr>
                    <tr id=2 onclick="playSuggest(2)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(2);</script>
                    </tr>
                    <tr id=3 onclick="playSuggest(3)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(3);</script>
                    </tr>
                    <tr id=4 onclick="playSuggest(4)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(4);</script>
                    </tr>
                    <tr id=5 onclick="playSuggest(5)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(5);</script>
                    </tr>
                    <tr id=6 onclick="playSuggest(6)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(6);</script>
                    </tr>
                    <tr id=7 onclick="playSuggest(7)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(7);</script>
                    </tr>
                    <tr id=8 onclick="playSuggest(8)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(8);</script>
                    </tr>
                    <tr id=9 onclick="playSuggest(9)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(9);</script>
                    </tr>
                </table>
            </form>
            <div id="informationen">        
                <div class="abschnitt">
                    <form id="playlist" action="home" method="post" autocomplete="off">
                        <script type="text/javascript">buildTablePlaylist();</script>
                    </form>
                </div>
                <div class="abschnitt zwei">
                    <table id="player">
                        <tr id="playerinfo">
                        <script>loadSongInfo("A Piano Piece");</script>
                        </tr>
                        <tr></tr>
                        <tr id="playeraudio">
                        <audio id="audio_with_controls" 
                               controls
                               src="songs/A_Piano_Piece.mp3" 
                               type="audio/mp3">
                        </audio>
                        </tr>
                        <tr>
                            <!--<td><button type="submit"><<</button><button type="submit">&#9633</button><button type="submit">></button><button type="submit">>></button></td>-->
                            <td></td><td><button type="submit">Upload</button></td>
                        </tr>
                    </table>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </body>
</html>