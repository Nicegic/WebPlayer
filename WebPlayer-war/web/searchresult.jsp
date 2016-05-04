<%-- 
    Document   : searchresult
    Created on : 03.05.2016, 10:49:54
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src ="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="logo.png" type="image/x-icon">
        <title>Suchergebnisse</title>
        <style>
            body{height:100%;background-image:url("login.png");color:white;}
            .result{width:80%;height:10%;margin-left:10%;background:white;opacity:0.8;border:5px solid white;border-radius:15px}
        </style>
        <script>
            $(function () {
                var search = ""+ $("#searchresults").children("input").val();
                $.post("home", {action:"searchresults", search:search}, function(result){
                    $("#searchresults").html(result);
                    $("#searchresults").append(""+search);
                });
            });
        </script>
    </head>
    <body>
        <h1>Deine Suchergebnisse:</h1>
        <input type="hidden" value="<%=request.getParameter("search")%>"
        <div id="searchresults">
            
        </div>
    </body>
</html>
