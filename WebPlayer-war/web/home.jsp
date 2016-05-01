<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Musikplayer</title>
        <link rel="icon" href="logo.png" type="image/x-icon">
        <link href="design.css" type="text/css" rel="stylesheet">
        <script language="javascript" type="text/javascript" src="homescript.js"></script>
    </head>
    <body >
        <div id="wrapper">
            <header>
            <h1>SLAPMusic!</h1>
            <image id="logo" src="logo.png" alt="Logo not found!"/>
            <button id="logout" onclick="document.location='/WebPlayer-war'">Logout</button>
            <div id="textarea">Hallo, <%=request.getParameter("username")%>! Schön, dich zu sehen!</div>
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