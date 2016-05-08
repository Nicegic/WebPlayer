<%-- 
    Document   : homepage
    Created on : 02.05.2016, 14:34:57
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="logo.png" type="image/x-icon">
        <!--<script src ="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>-->
        <script src="jquery-2.2.3.min.js"></script>
        <title>SLAPMusic</title>
        <style>
            body{background-image:url("login.png");height:100%;color:white;font-family:sans-serif;}
            h1{float:left;margin:2%;}
            hr{color:lightgrey;clear:both;}
            #logo{width:50px;height:50px;float:left;margin-top:2%;}
            #logout{float:right; margin:2%;}
            #userinfo{float:right;margin:2%;}
            #username{float:right;}
            #search{float:right;margin:2%;}
            .suggestion{background-color:white;opacity:0.75;border-radius:10px;border:2px solid white;width:200px;height:110px;
                        font-size:12px; font-weight:bold;color:black;float:left; margin:2%;padding-left:5px;overflow:hidden;}
            .suggestion:hover{opacity:0.9;border:2px solid black;transition:0.5s;}
            .playlist{font-size:14px;font-weight:bold;color:black;height:100px;border:1px solid black;background:grey;overflow-x:hidden;overflow-y:auto;opacity:0.5;}
            .song{font-size:16px;font-weight:bold;color:black;height:50px;border:1px solid darkblue; width:80%;float:right;background:lightgrey;opacity:0.75;}
            .editbutton{font-weight:bold;color:white;height:25px;border:1px solid white;width:18%;float:left;background:darkgrey;margin-top:25px;}
            .songfirst{font-size:16px;font-weight:bold;color:black;height:50px;border:1px solid darkblue; width:80%;float:right;background:lightgrey;opacity:0.75;}
            .hint{font-size:11px;font-weight:bold; color:black; height:50px;border:1px solid red; width:80%;float:right;background:lightgrey;opacity:0.75}
            .ratingsel{color:yellow;}
            .ratingnotsel{display:inline-block;width:0.6em;font-size:1.4em;color:#ccc;cursor:pointer;text-shadow:0 0 1px #666;}
            .song:hover{opacity:0.9;border:1px solid red;transition:0.5s;}
            .songfirst:hover{opacity:0.9;border:1px solid red;transition:0.5s;}
            .editbutton:hover{border:1px solid red;transition:0.5s;}
            #playerthings{margin:5%;}
            #playlists{float:left;width:45%;border:2px solid grey;border-radius:5px;margin-right:5%;
                       height:300px;overflow-x:hidden;overflow-y:auto;}
            #player{float:left;width:45%;border: 2px solid grey; border-radius:5px;}
            #bewertung{unicode-bidi:bidi-override;direction:rtl;float:left;}
            #bewertung span{display:inline-block;width:1.1em;font-size:2.8em;color:#ccc;cursor:pointer;text-shadow: 0 0 1px #666;}
            #bewertung span:hover,#bewertung span:hover ~ span{
                color:gold;text-shadow: 0 0 5px #e2e2e2;
            }
            #bewertung span:active, #bewertung span:active ~ span{
                color:yellow;
            }
        </style>
        <script>
            //sobald die Seite geladen ist, wird der User angemeldet und alle zu ihm gehörenden Playlists werden geladen
            $(function () {
                username = $("#username").html();
                $.post("home", {action: "login", username: username}, loadPlaylists());
            });
            //beim Klick auf den Logout-Button wird der User abgemeldet und auf die Startseite weitergeleitet
            function logout() {
                $.post("home", {action: "logout", username: username});
                window.location = "/WebPlayer-war/";
            }
            //sobald ein Lied fertig ist, wird das nächste automatisch nachgeladen.
            //Dabei wird zwischen normalem Weiterspielen und Weiterspielen in der Playlist
            //unterschieden.
            function loadNext() {
                var inplaylist = $("#audioplayer").data("inPlaylist");
                if (inplaylist == true) {
                    $.post("home", {action: "loadNext", username: username}, function (result) {
                        $('#audioplayer').attr('src', result);
                        $('#audioplayer').load();
                        $('#audioplayer').trigger('play');
                        loadPlaylistSongInfo();
                    });
                } else {
                    playSuggest(1);
                }
            }
            //lädt für den übergebenen Div eine neue Empfehlung
            function loadSuggest(divid) {
                $.post("home", {action: "suggest"}, function (result) {
                    var id = "#" + divid;
                    $(id).html(result);
                });
            }
            //bei Klick auf eine der Empfehlungen wird das Lied abgespielt und die
            //zugehörige Information vom Server abgerufen
            function playSuggest(divid) {
                var songid = $("#" + divid).children("input").val();
                playSong(songid);
                loadSuggest(divid);
            }
            //lädt den Songpfad und startet die Wiedergabe des Songs
            function playSong(songid) {
                $.post("home", {action: "play", songid: songid}, function (result) {
                    $('#audioplayer').attr('src', result);
                    $('#audioplayer').load();
                    $('#audioplayer').trigger('play');
                    loadSongInfo(songid);
                });
            }
            //lädt die Songinformationen des aktuellen Songs und zeigt diese an
            function loadSongInfo(songid) {
                $.post("home", {action: "info", songid: songid}, function (result) {
                    $('#songinfos').html(result);
                });
            }
            //Spezialfall von play
            //aufgrund der anderen behandlung wird hier nicht die Songid übergeben
            //sondern der wievielte Song in der wievielten Playlist gespielt werden soll
            //der zurückgegebene Songpfad wird eingebunden und das Audio abgespielt
            function playSongInPlaylist(songno, playlistno) {
                $("#audioplayer").data("inPlaylist", true);
                $.post("home", {action: "playPlaylistSong", username: username, playlistno: playlistno, songno: songno}, function (result) {
                    $("#audioplayer").attr("src", result);
                    $('#audioplayer').load();
                    $('#audioplayer').trigger('play');
                    loadPlaylistSongInfo();
                });
            }
            
            //Spezialfall von loadSongInfo()
            //lädt zum aktuell aktiven Playlistsong die Songinformationen
            function loadPlaylistSongInfo() {
                $.post("home", {action: "loadPlaylistSongInfo", username: username}, function (result) {
                    $('#songinfos').html(result);
                });
            }
            //bewertet den aktuellen Song mit der übergebenen Anzahl an Sternen
            function review(stars){
                var songid=$("#songinfos").children("input").val();
                for(var i=0;i<stars;i++){
                    $("#"+i+"star").attr("class","ratingsel");
                }
                for(var i=stars;i<5;i++){
                    $("#"+i+"star").attr("class","ratingnotsel");
                }
                $.post("home", {action:"review", songid:songid, stars:stars, username:username},function(){
                    if($("#audioplayer").data("inPlaylist")==true){
                        loadPlaylistSongInfo();
                    }else{
                        loadSongInfo(songid);
                    }
                });
            }
            //wurde eine neue Playlist erstellt, wird diese hiermit auf dem Server ebenfalls hinterlegt
            function sendNewPlaylist() {
                username = $("#username").html();
                var playlistname = $("#newPlaylistName").children("input").val();
                $.post("home", {action: "addPlaylist", playlistname: playlistname, username: username}, loadPlaylists());
            }
            //lädt alle Playlists des aktiven Users
            function loadPlaylists() {
                username = $("#username").html();
                $.post("home", {action: "loadPlaylists", username: username}, function (result) {
                    $("#existingplaylists").html(result);
                });
                $("#newplaylist").html("<div class=\"playlist\" onclick=\"addPlaylist()\">Klicke hier, um eine neue Playlist zu erstellen.</div>");
            }
            //soll eine neue Playlist hinzugefügt werden erscheinen ein Eingabefeld und ein Button
            //für die Benennung der neuen playlist und zum Erstellen dieser
            function addPlaylist() {
                $("#newplaylist").html("Bitte gib hier den Namen der neuen Playlist ein: <input id=\"newplaylistname\" type=\"text\"/> <button onclick=\"sendNewPlaylist()\">erstellen</button>");
            }
        </script>
    </head>
    <body>
        <div id="header">
            <h1> SLAPMusic! </h1>
            <image id="logo" src="logo.png" alt="logo"/>
            <button id="logout" onclick="logout();">logout</button>
            <form name="search" target="home" action="home" method="post"><input type="hidden" name="action" value="search"/><input id="search" name="search" type="search" /></form>
            <div id="userinfo">Hallo! <div id="username"><%=request.getParameter("username")%></div> Schön, dich zu sehen, </div>
        </div>
        <hr>
        <div id="suggests">
            <div class="suggestion" id="1" onclick="playSuggest(1);"><script>loadSuggest(1);</script></div>
            <div class="suggestion" id="2" onclick="playSuggest(2);"><script>loadSuggest(2);</script></div>
            <div class="suggestion" id="3" onclick="playSuggest(3);"><script>loadSuggest(3);</script></div>
            <div class="suggestion" id="4" onclick="playSuggest(4);"><script>loadSuggest(4);</script></div>
            <div class="suggestion" id="5" onclick="playSuggest(5);"><script>loadSuggest(5);</script></div>
            <div class="suggestion" id="6" onclick="playSuggest(6);"><script>loadSuggest(6);</script></div>
            <div class="suggestion" id="7" onclick="playSuggest(7);"><script>loadSuggest(7);</script></div>
            <div class="suggestion" id="8" onclick="playSuggest(8);"><script>loadSuggest(8);</script></div>
        </div>
        <hr>
        <div id="playerthings">
            <div id="playlists">
                <div id="existingplaylists">
                    <div class="playlist">
                        Dies ist eine Playlist mit einem Verweis auf den ersten Song.
                        <form name="edit" target="home" action="home" method="post"><input type="hidden" name="action" value="edit"/><input type="hidden" name="playlist" value="0"/><input type="hidden" name="username" value="<%=request.getParameter("username")%>"/><button class="editbutton">Edit</button></form>
                        <div class="songfirst">Dies ist der erste Song der Playlist.</div>
                        <div class="song"> Dies sind alle folgenden Songs der Playlist.</div>
                    </div>
                    <div class="playlist">
                        Dies ist eine Playlist mit einem Verweis auf den ersten Song.
                        <form name="edit" target="home" action="home" method="post"><input type="hidden" name="action" value="edit"/><input type="hidden" name="playlist" value="0"/><input type="hidden" name="username" value="<%=request.getParameter("username")%>"/><button class="editbutton">Edit</button></form>
                        <div class="songfirst">Dies ist der erste Song der Playlist.</div>
                        <div class="song"> Dies sind alle folgenden Songs der Playlist.</div>
                    </div>
                    <div class="playlist">
                        Dies ist eine Playlist mit einem Verweis auf den ersten Song.
                        <form name="edit" target="home" action="home" method="post"><input type="hidden" name="action" value="edit"/><input type="hidden" name="playlist" value="0"/><input type="hidden" name="username" value="<%=request.getParameter("username")%>"/><button class="editbutton">Edit</button></form>
                        <div class="songfirst">Dies ist der erste Song der Playlist.</div>
                        <div class="song"> Dies sind alle folgenden Songs der Playlist.</div>
                    </div>
                </div>
                <div id="newplaylist">
                    <button class="editbutton" onclick="loadPlaylists()">reloadPlaylists</button>
                    <div class="playlist" onclick="addPlaylist()">Klicke hier, um eine neue Playlist zu erstellen.</div>
                </div>
            </div>
            <div id="player">
                <div id="songinfos"><script> loadSongInfo(2);</script></div>
                <br>
                <audio id="audioplayer" src="songs/a_brilliant_idea_terrasound.mp3" controls onended="loadNext();" type="audio/mp3"></audio>
                <div id="bewertung">
                    <span class="star" id="1star" onclick="review(5);">
                        &#9733;
                    </span>
                    <span class="star" id="2star" onclick="review(4);">
                        &#9733;
                    </span>
                    <span class="star" id="3star" onclick="review(3);">
                        &#9733;
                    </span>
                    <span class="star" id="4star" onclick="review(2);">
                        &#9733;
                    </span>
                    <span class="star" id="5star" onclick="review(1);">
                        &#9733;
                    </span>
                </div>
            </div>
        </div>
    </body>
</html>
