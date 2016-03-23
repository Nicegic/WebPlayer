<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Musikplayer</title>
        <style>
            html, body {height: 100%; margin: 0; overflow: auto; padding: 0; position: relative; color: white; background-image: url("login.png");}
            hr{color: white;}
            #wrapper { bottom: 0; height: 100%; margin: 0 auto; position: relative; top: 0; width: 100%;}
            td{/*border: 1px solid red;*/}
            th {padding-bottom: 20px;}
            /*button[type="submit"]{border:4px solid rgb(255,96,80); background-color:red; border-radius:30px; color: white; width:40px; height:40px; font-size:15px;}
            */#search {position: absolute; top: 0; right: 0; margin: 1%;}
            #logout{position: absolute; top: 0; right: 0; margin: 1%; margin-right: 11%;}
            #textarea{position: absolute; top: 0; right: 0; margin: 1%; margin-right: 15%}
            #player{margin-right: 10%; width: 80%}
            #empfehlungForm{}
            #playlist{}
            #empfehlungen {border: 3px solid grey; width: 80%; margin-left: 10%;}
            #informationen {position: absolute; border: 3px solid grey; width: 80%; margin-left: 10%; margin-top: 1%;/*margin-bottom: 10%;*/}
            .abschnitt {float: left; width: 50%; height: 30%; /*overflow: scroll*/}
            .zwei  { width: 50%; height: 30%; margin-top: 3%; overflow: hidden;}
            .clear { clear: both; }
        </style>
        <script>
            function buildTableEmpfehlungen(){
                var tabelle="<table id=\"empfehlungen\"><tr><th>Empfehlungen</th></tr>";
                for(var i=0; i<10; i++){
                    tabelle=tabelle+"<tr id=\""+i+"\" name=\"play\" value=\"play\" onclick=\"document.location='HomeServlet'\" method=\"post\">";
                    var titel = "<td>Titel</td>";
                    var interpret = "<td>Interpret</td>";
                    var dauer = "<td>Dauer</td>";
                    var genre = "<td>Genre</td>";
                    var bewertung = "<td>Bewertung</td>";
                    var buttons = "<td><button type=\"submit\" name=\"loeschen\" value=\"loeschen\">X</button></td>";
                    tabelle=tabelle+titel+interpret+dauer+genre+bewertung+buttons+"</tr>";
                }
                tabelle=tabelle+"</table>";
                document.getElementById("empfehlungForm").innerHTML = tabelle;
            };
            function buildTablePlaylist(){
                var tabelle="<table><th>Inhalt der Playlisten</th>";
                for(var i=0; i<15; i++){
                    tabelle=tabelle+"<tr id=\""+i+"\" name=\"play\" value=\"play\" onclick=\"document.location='HomeServlet'\" method=\"post\">";
                    var titel = "<td>Titel</td>";
                    var interpret = "<td>Interpret</td>";
                    var dauer = "<td>Dauer</td>";
                    var genre = "<td>Genre</td>";
                    var bewertung = "<td>Bewertung</td>";
                    var buttons = "<td><button type=\"submit\" name=\"play\" value=\"1\">></button><button type=\"submit\" name=\"loeschen\" value=\"loeschen\">X</button></td>";
                    tabelle=tabelle+titel+interpret+dauer+genre+bewertung+buttons+"</tr>";
                }
                tabelle=tabelle+"</table>";
                document.getElementById("playlist").innerHTML = tabelle;
            };
        </script>
    </head>
    <body >
        <div id="wrapper">
            <h1>SLAPMusic!</h1>
            <div id="textarea">Test user</div>
            <input id="search">
            <button id="logout" type="submit">Logout</button>
            <hr>
            <form id="empfehlungForm" action="HomeServlet" method="post" autocomplete="off">
                <script type="text/javascript">buildTableEmpfehlungen();</script>
            </form>
            <div id="informationen">        
                <div class="abschnitt">
                    <form id="playlist" action="HomeServlet" method="post" autocomplete="off">
                        <script type="text/javascript">buildTablePlaylist()();</script>
                    </form>
                </div>
                <div class="abschnitt zwei">
                    <table id="player">
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Albumcover</td>
                        </tr>
                        <tr>
                            <td><button type="submit"><<</button><button type="submit">&#9633</button><button type="submit">></button><button type="submit">>></button></td>
                            <td></td><td><button type="submit">Upload</button></td>
                        </tr>
                    </table>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </body>
</html>