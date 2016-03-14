<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Musikplayer</title>
        <style>
            html, body {height: 100%; margin: 0; overflow: auto; padding: 0; position: relative; color: white; background-image: url("login.png");}
            #wrapper { bottom: 0; height: 100%; margin: 0 auto; position: relative; top: 0; width: 100%;}
            td{/*border: 1px solid red;*/}
            th {padding-bottom: 20px;}
            /*button[type="submit"]{border:4px solid rgb(255,96,80); background-color:red; border-radius:30px; color: white; width:40px; height:40px; font-size:15px;}
            */#search {position: absolute; top: 0; right: 0; margin: 1%;}
            #logout{position: absolute; top: 0; right: 0; margin: 1%; margin-right: 11%;}
            #textarea{position: absolute; top: 0; right: 0; margin: 1%; margin-right: 15%}
            #player{margin-right: 10%; width: 80%}
            #empfehlungen {border: 3px solid grey; width: 80%; margin-left: 10%;}
            #informationen {position: absolute; border: 3px solid grey; width: 80%; margin-left: 10%; margin-top: 1%;/*margin-bottom: 10%;*/}
            .abschnitt {float: left; width: 50%; height: 30%; /*overflow: scroll*/}
            .zwei  { width: 50%; height: 30%; margin-top: 3%; overflow: hidden;}
            .clear { clear: both; }
        </style>
    </head>
    <body>
        <div id="wrapper">
            <h1>Musikplayer</h1>
            <div id="textarea">Test user</div>
            <input id="search">
            <button id="logout" type="submit">Logout</button>
            <form id="loeschen" action="index.html" method="post" autocomplete="off"></form>
            <table id="empfehlungen">
                <tr>
                    <th>Empfehlungen</th>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button form="loeschen" type="submit" name="action">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
                <tr>
                    <td>Titel</td>
                    <td>Interpret</td>
                    <td>Dauer</td>
                    <td>Genre</td>
                    <td>Bewertung</td>
                    <td><button type="submit">></button><button type="submit">X</button></td>
                </tr>
            </table>
            <div id="informationen">        
                <div class="abschnitt">
                    <table>
                        <th>Inhalt der Playlisten</th>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>       
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr><tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                        <tr>
                            <td>Titel</td>
                            <td>Interpret</td>
                            <td>Dauer</td>
                            <td>Genre</td>
                            <td>Bewertung</td>
                            <td><button type="submit">></button><button type="submit">X</button></td>
                        </tr>
                    </table>
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