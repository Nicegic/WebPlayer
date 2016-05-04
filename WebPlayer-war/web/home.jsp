<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Musikplayer</title>
        <link rel="icon" href="logo.png" type="image/x-icon">
        <script src ="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <style>
            body {size: auto; color: white; background-image: url("login.png");}
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
            #wrapper {height: 100%; width: 100%;}
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
            #playlists{width:90%;}
            #suggestions{border: 5px solid black; border-radius:25px;}
            #informationen {border: 3px solid grey; width: 80%; margin-left: 10%;}
            .abschnitt {float: left; width: 55%; height: 200px; overflow-x:hidden; overflow-y: auto;}
            .zwei  { width: 40%; height: 30%; margin-top: 3%; margin-left:1%; overflow: hidden;}
            .clear { clear: both; }
            .suggestion{
                float:left;
                width:200px;
                height:150px;
                margin:1%;
                background-color:white;
                opacity:0.8;
                border: 2px solid grey;
                border-radius:15px;
                color:black;
                padding:5px;
            }
            .suggestion:hover{
                opacity:0.9;
                border:2px solid black;
                border-radius:15px;
                transition:0.5s;
            }
            p{
                font-family:sans-serif;
                font-weight:bolder;
            }
        </style>
        <script>
            $(document).ready(function () {
                $('.suggest').click(playSuggest())
            });
            function buildEmpfehlungenRow(rowid) {
                var xhttp = new XMLHttpRequest();
                var buttons = "<td><div id=\"button\" onclick=\"buildEmpfehlungenRow(" + rowid + ")\">X</div></td>";
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById(rowid).innerHTML = xhttp.responseText + buttons + "</tr>";
                    }
                }
                xhttp.open("POST", "home", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("action=suggest&rowid=" + rowid);
            }

            function buildTablePlaylist() {
                var tabelle = "<th> Inhalt der Playlists</th>";
                for (var i = 0; i < 15; i++) {
                    tabelle = tabelle + "<tr id=p\"" + i + "\" onclick=\"loadPlaylist(p" + i + ")\">";
                    var playlistname = "<td>Name" + i + "</td>";
                    tabelle = tabelle + playlistname + "</tr>";
                }
                document.getElementById("playlists").innerHTML = tabelle;
            }

            function loadPlaylist(id) {
                var tabelle = "";
                for (var i = 0; i < list.size; i++) {
                    tabelle = tabelle + "<tr id=song\"" + i + "\" name=\"play\" value=\"play\" onclick=\"\">";
                    var titel = "<td>Titel</td>";
                    var interpret = "<td>Interpret</td>";
                    var dauer = "<td>Dauer</td>";
                    var genre = "<td>Genre</td>";
                    var bewertung = "<td>Bewertung</td>";
                    tabelle = tabelle + titel + interpret + dauer + genre + bewertung + "</tr>";
                }
                tabelle = tabelle + "</table>";
                document.getElementById(id).innerHTML = tabelle;

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
            function loadSuggestNew(divid){
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById(divid).innerHTML=xhttp.responseText;
                    }
                }
                xhttp.open("POST", "home", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("action=suggest");
            }
        </script>
    </head>
    <body >
        <div id="wrapper">
            <header>
                <h1>SLAPMusic!</h1>
                <image id="logo" src="logo.png" alt="Logo not found!"/>
                <button id="logout" onclick="document.location = 'index.html'">Logout</button>
                <div id="textarea">${username}</div>
                <input id="search">
            </header>
            <hr>
                <div class="suggestion" onclick="playSuggest()" id="suggest0">
                    <script>loadSuggestNew("suggest0");</script>
                </div>
                <div class="suggestion" id="suggest1">
                    <script>loadSuggestNew("suggest1");</script>
                </div>
                <div class="suggestion" id="suggest2">
                    <script>loadSuggestNew("suggest2");</script>
                </div>
            <!--<form id="empfehlungForm" action="home" method="post" autocomplete="off">
                <table id="empfehlungen"><tr><th>Empfehlungen</th></tr>
                    <tr>
                        <th>Titel</th>
                        <th>Interpret</th>
                        <th>Dauer</th>
                        <th>Genre</th>
                        <th>durchschnittliche Bewertung</th>                    
                    </tr>
                    <tr id=0 class="suggest" onclick="playSuggest(0)">
                    <script type="text/javascript">buildEmpfehlungenRow(0);</script>
                    </tr>   
                    <tr id=1 class="suggest" onclick="playSuggest(1)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(1);</script>
                    </tr>
                    <tr id=2 class="suggest" onclick="playSuggest(2)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(2);</script>
                    </tr>
                    <tr id=3 class="suggest" onclick="playSuggest(3)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(3);</script>
                    </tr>
                    <tr id=4 class="suggest" onclick="playSuggest(4)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(4);</script>
                    </tr>
                    <tr id=5 class="suggest" onclick="playSuggest(5)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(5);</script>
                    </tr>
                    <tr id=6 class="suggest" onclick="playSuggest(6)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(6);</script>
                    </tr>
                    <tr id=7 class="suggest" onclick="playSuggest(7)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(7);</script>
                    </tr>
                    <tr id=8 class="suggest" onclick="playSuggest(8)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(8);</script>
                    </tr>
                    <tr id=9 class="suggest" onclick="playSuggest(9)" method="post">
                    <script type="text/javascript">buildEmpfehlungenRow(9);</script>
                    </tr>
                </table>
            </form>-->
            <!--<div id="informationen">        -->
            <br>
            <br>
            <br>
                <div class="abschnitt">
                    <table id="playlists">
                        <script type="text/javascript">buildTablePlaylist();</script>
                    </table>
                </div>
                <div class="abschnitt zwei">
                    <table id="player">
                        <tr id="playerinfo">
                        <script>loadSongInfo("A brilliant Idea");</script>
                        </tr>
                        <tr></tr>
                        <tr id="playeraudio">
                        <audio id="audio_with_controls" 
                               controls
                               src="songs/a_brilliant_idea_terrasound.mp3" 
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
            <!--</div>-->
        </div>
    </body>
</html>