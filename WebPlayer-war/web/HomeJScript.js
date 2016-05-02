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
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            document.getElementById("playlist").innerHTML = xhttp.responseText;
        }
    }
    xhttp.open("POST", "home", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("action=playlistlist&user=nicolas");
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