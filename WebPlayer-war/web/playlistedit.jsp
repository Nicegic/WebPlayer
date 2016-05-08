<%-- 
    Document   : playlistedit
    Created on : 04.05.2016, 07:50:07
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="jquery-2.2.3.min.js"></script>
        <link rel="icon" href="logo.png" type="image/x-icon">
        <title>Playlistedit</title>
        <style>
            body{height:100%;background-image:url("login.png");color:white;}
            .big{width:450px;margin-left:5%;background-color:white;opacity:0.8;border:2px solid grey;border-radius:15px;float:left;height:475px;overflow-y:auto;overflow-x:hidden;color:black;font-weight:bold}
            .suggest{width:430px;height:100px;background-color:lightgrey;margin-bottom:5px;opacity:0.75; border:2px solid black; border-radius:10px;}
            .buttons{height:20px;background-color:darkgoldenrod;opacity:0.9;color:black;border:2px solid white;border-radius:5px;}
        </style>
        <script>
            //sobald die Seite aufgebaut ist, wird die zu editierende Playlist geladen
            //gleichzeitig werden alle momentan vorhandenen Songs geladen
            //username und playlistnr sind in der Seite zwischengespeichert
            $(function () {
                username=$("#username").val();
                playlistno = $("#playlistno").val();
                $.post("home", {action: "login", username:username},loadPlaylist());
                $.post("home", {action: "showsongs"}, function (result) {
                    $("#allsongs").html(result);
                });
            });
            
            //lädt die zu editierende Playlist
            function loadPlaylist() {
                
                $.post("home", {action: "loadplaylist", playlistno: playlistno, username: username}, function (result) {
                    $("#songs").html(result);
                });
            }
            //sendet eine Editierungsanfrage an den Server
            function editPlaylist(songid, remove) {
                $.post("home", {action: "editplaylist", playlistno: playlistno, username: username, songid: songid, remove: remove}, loadPlaylist());
                loadPlaylist();
            }
        </script>
    </head>
    <body>
        <h1>Ändere hier deine Playlist!</h1>
        <h2> Einfach die Songs per Drag&Drop hin- und herziehen. </h2>
        <div id="playlist">
            <input type="hidden" id="playlistno" value="<%=request.getParameter("playlist")%>"/>
            <input type="hidden" id="username" value="<%=request.getParameter("username")%>"/>
            <div id="songs" class="big">

            </div>
        </div>
        <div id="allsongs" class="big">

        </div>
    </body>
</html>
