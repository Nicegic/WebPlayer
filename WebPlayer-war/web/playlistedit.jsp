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
        <script src="jquery-ui.min.js"></script>
        <link rel="icon" href="logo.png" type="image/x-icon">
        <title>Playlistedit</title>
        <style>
            body{height:100%;background-image:url("login.png");color:white;}
            #playlist{width:450px;margin-left:5%;background-color:white;opacity:0.8;border:2px solid grey;border-radius:15px;float:left;height:475px;overflow-y:auto;overflow-x:hidden;color:black;font-weight:bold}
            #allsongs{width:450px;margin-right:5%;background-color:white;opacity:0.8;border:2px solid grey;border-radius:15px;float:left;height:475px;overflow-y:auto;overflow-x:hidden;color:black;font-weight:bold}
            .suggest{width:430px;height:80px;background-color:lightgrey;margin-bottom:5px;opacity:0.75; border:2px solid black; border-radius:10px;}
        </style>
        <script>
            $(function () {
                $.post("home", {action: "showsongs"}, function (result) {
                    $("#allsongs").html(result);
                    $(".suggest").draggable({cursor: "move", containment: "document", stack: ".suggest", snap: ".big", helper: "clone", revert: true});
                    $("#playlist").droppable({drop: handleDrop});
                    $("#allsongs").droppable({drop: handleDrop2});
                });
                loadPlaylist();
            });
            function handleDrop(event, ui) {
                var draggable = ui.draggable;
                draggable.draggable('disable');
                draggable.draggable('option', 'revert', false);
                var songid = draggable.children("input").val();
                editPlaylist(songid, false);
            }

            function handleDrop2(event, ui) {
                var draggable = ui.draggable;
                var songid = draggable.children("input").val();
                var found = false;
                $("#songs").children().each(function () {
                    if ($(this).children("input").val() == songid)
                        found = true;
                });
                if (found) {
                    editPlaylist(songid, true);
                } else {
                    loadPlaylist();
                }
            }

            function loadPlaylist() {
                var playlistno = $("#playlistno").val();
                var username = $("#username").val();
                $.post("home", {action: "loadplaylist", playlistno: playlistno, username: username}, function (result) {
                    $("#songs").html(result);
                });
            }

            function editPlaylist(songid, remove) {
                var playlistno = $("#playlistno").val();
                var username = $("#username").val();
                $.post("home", {action: "editplaylist", playlistno: playlistno, username: username, songid: songid, remove: remove}, loadPlaylist());
                loadPlaylist();
            }
        </script>
    </head>
    <body>
        <h1>Ändere hier deine Playlist!</h1>
        <h2> Einfach die Songs per Drag&Drop hin- und herziehen. </h2>
        <div id="playlist" class="big">
            <input type="hidden" id="playlistno" value="<%=request.getParameter("playlist")%>"/>
            <input type="hidden" id="username" value="<%=request.getParameter("username")%>"/>
            <div id="songs">

            </div>
        </div>
        <div id="allsongs" class="big">

        </div>
    </body>
</html>
