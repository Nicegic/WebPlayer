<%-- 
    Document   : searchresult
    Created on : 03.05.2016, 10:49:54
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="jquery-2.2.3.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="logo.png" type="image/x-icon">
        <title>Suchergebnisse</title>
        <style>
            body{height:100%;background-image:url("login.png");color:white;}
            #searchresults{margin-left:10%;align-self:center;height:600px;width:700px;overflow-x:hidden;overflow-y:auto;border:5px solid grey;border-radius:20px;}
            .result{color:black;font-weight:bold;width:680px;height:120px;background:white;opacity:0.8;border:5px solid white;border-radius:15px}
        </style>
        <script>
            $(function () {
                var search = $("#searchstring").val();
                $.post("home", {action: "searchresults", search: search}, function (result) {
                    $("#searchresults").html(result);
                });
            });
        </script>
    </head>
    <body>
        <h1>Deine Suchergebnisse:</h1>
        <input type="hidden" id="searchstring" value="<%=request.getParameter("search")%>"/>
        <div id="searchresults">

        </div>
    </body>
</html>
