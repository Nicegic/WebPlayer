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
            .song{font-size:11px;font-weight:bold;color:black;height:50px;border:1px solid darkblue; width:80%;float:right;background:lightgrey;opacity:0.75;}
            .editbutton{font-weight:bold;color:white;height:25px;border:1px solid white;width:18%;float:left;background:darkgrey;margin-top:25px;}
            .songfirst{font-size:11px;font-weight:bold;color:black;height:50px;border:1px solid darkblue; width:80%;float:right;background:lightgrey;opacity:0.75;}
            .hint{font-size:11px;font-weight:bold; color:black; height:50px;border:1px solid red; width:80%;float:right;background:lightgrey;opacity:0.75}
            .song:hover{opacity:0.9;border:1px solid red;transition:0.5s;}
            .songfirst:hover{opacity:0.9;border:1px solid red;transition:0.5s;}
            .editbutton:hover{border:1px solid red;transition:0.5s;}
            #playerthings{margin:5%;}
            #playlists{float:left;width:45%;border:2px solid grey;border-radius:5px;margin-right:5%;
                       height:300px;overflow-x:hidden;overflow-y:auto;}
            #player{float:left;width:45%;border: 2px solid grey; border-radius:5px;}
        </style>
        <script>
            $(function(){
                var username=$("#username").html();
               $.post("home", {action:"login", username:username});
               loadPlaylists();
            });
            function logout(){
                var username=$("#username").html();
                $.post("home", {action:"logout", username:username});
                window.location="/WebPlayer-war/";
            }
            function loadNext() {
                playSuggest(1);
            }

            function loadSuggest(divid) {
                $.post("home", {action: "suggest"}, function (result) {
                    var id = "#" + divid;
                    $(id).html(result);
                });
            }
            function playSuggest(divid) {
                playSong(divid);
                loadSuggest(divid);
            }
            function playSong(divid) {
                var id = "#" + divid;
                var songid = $(id).children("input").val();
                $.post("home", {action: "play", songid: songid}, function (result) {
                    $('#audioplayer').attr('src', result);
                    $('#audioplayer').load();
                    $('#audioplayer').trigger('play');
                });
                loadSongInfo(songid);
            }
            function loadSongInfo(songid) {
                $.post("home", {action: "info", songid: songid}, function (result) {
                    $('#songinfos').html(result);
                });
            }
            function loadPlaylists() {
                var username=$("#username").html();
                $.post("home", {action:"loadPlaylists", username:username}, function(result){
                   $("#existingplaylists").html(result); 
                });
                $("#newplaylist").html("<div class=\"playlist\" onclick=\"addPlaylist()\">Klicke hier, um eine neue Playlist zu erstellen.</div>");
            }
            function addPlaylist(){
                $("#newplaylist").html("Bitte gib hier den Namen der neuen Playlist ein: <input id=\"newplaylistname\" type=\"text\"/> <button onclick=\"sendNewPlaylist()\">erstellen</button>");
            }
            
            function sendNewPlaylist(){
                var username=$("#username").html();
                var playlistname=$("#newPlaylistName").children("input").val();
                $.post("home", {action:"addPlaylist", playlistname:playlistname, username:username},loadPlaylists());
                loadPlaylists();
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
                        <button class="editbutton">Edit</button>
                        <div class="songfirst">Dies ist der erste Song der Playlist.</div>
                        <div class="song"> Dies sind alle folgenden Songs der Playlist.</div>
                    </div>
                    <div class="playlist">
                        Dies ist eine Playlist mit einem Verweis auf den ersten Song.
                        <button class="editbutton">Edit</button>
                        <div class="songfirst">Dies ist der erste Song der Playlist.</div>
                        <div class="song"> Dies sind alle folgenden Songs der Playlist.</div>
                    </div>
                </div>
                <div id="newplaylist">
                    <div class="playlist" onclick="addPlaylist()">Klicke hier, um eine neue Playlist zu erstellen.</div>
                </div>
            </div>
            <div id="player">
                <div id="songinfos"><script> loadSongInfo(2);</script></div>
                <br>
                <audio id="audioplayer" src="songs/a_brilliant_idea_terrasound.mp3" controls onended="loadNext();" type="audio/mp3"></audio>
            </div>
        </div>
    </body>
</html>
