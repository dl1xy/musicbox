<%
	session.setAttribute("header_type","main");
	session.setAttribute("admin_mode", new Boolean(false));
%>
<jsp:include page="/header.jsp" />
<h3 style="text-align:center">Hallo  <% out.print(session.getAttribute("username")); %></h3>
<p>
Vielen Dank, daß Du <b>music.box</b> testest. Mit der <b>music.box</b> kannst Du Songs suchen und zur Playlist hinzufügen, eigene MP3s hochladen oder einfach nur abstimmen, welcher Song als nächstes gespielt wird, .
</p>
<p>
Um Dir den Einstieg zu erleichtern, ist hier eine kurze Übersicht der Funktionen und Anmerkungen:
</p>

<p>&nbsp;</p>
<h4><i class="fa fa-list"></i> Playlist</h4>
<p>
In der Playlist werden alle Songs angezeigt, die von Dir oder anderen <b>music.box</b> Anwendern hinzugefügt wurden und nacheinander abgespielt werden. Die Abspielreihenfolge kann durch Abstimmung beeinflusst werden.
</p>
<p>
<button class="btn btn-small btn-success" type="button"><i class="fa fa-chevron-circle-up"></i></button> Gefällt mir
</p>
<p>
<button class="btn btn-small btn-danger" type="button"><i class="fa fa-chevron-circle-down"></i></button> Gefällt mir nicht
</p>
<p>
Songs, die Du selbst zur Playlist hinzugefügt hast, kannst Du auch wieder entfernen.
</p>
<p>
<button class="btn btn-small btn-warning" type="button"><i class="fa fa-trash"></i></button> Song entfernen
</p>

<p>&nbsp;</p>
<h4><i class="fa fa-search"></i> Suche</h4>
<p>
Alle in der music.box vorhandenen MP3-Dateien können über die Suche gefunden werden, sofern sie über die notwendigen ID3-Tags verfügen. Du kannst auch "Auf gut Gl&uuml;ck" suchen !<br>
<br><b>ACHTUNG</b>: Songs/Artists/Alben, die Umlaute enthalten (Die &Auml;rzte, Mot&oouml;rhead, R&ouml;yksopp etc.), können teilweise nicht abgespielt werden und blockieren dann die Playlist. Bitte vermeide diese Songs !
</p>
<p>
<button class="btn btn-primary"><i class="fa fa-list"></i></button> Zur Playlist hinzufügen 
</p>
<p>
<button class="btn btn-success"><i class="fa fa-check"></i></button> In der Playlist
</p>

<p>&nbsp;</p>
<h4><i class="fa fa-upload"></i> Upload</h4> 
<p>
Wenn Du m&ouml;chtest, kannst Du eigene MP3s hochladen. Sie werden sofort zur Playliste hinzugefügt.
</p>

<p>&nbsp;</p>
<h4><i class="fa fa-bar-chart"></i> Stats</h4> 
<p>
Hier findest du Angaben, von welchem Nutzer die meisten Songs gespielt wurden, wer am Besten bewertet wurde und wer die meisten Songs hochgeladen hat.
</p>

<p>&nbsp;</p>
<h4><i class="fa fa-comment"></i> Feedback</h4> 
<p>
Hier kannst du eine kurze Nachricht mit Kritik, Lob, Anregungen oder was Du mir schon immer mal sagen wolltest an mich schicken. 
</p>

<p>&nbsp;</p>
<h4><i class="fa fa-times"></i> Logout</h4> 
<p>
Wenn Du keinen Bock mehr auf die <b>music.box</b> hast, kannst Du dich hier ausloggen (und hoffentlich bald wiederkommen :-)  
</p>

<p>&nbsp;</p>
<h4><i class="fa fa-bug"></i> Bugs</h4> 
<p>
Leider ist die <b>music.box</b> noch nicht fehlerfrei. Bekannte Bugs sind

 <ul>
  <li>Dateien, die via Upload hochgeladen wurden, tauchen nicht in den Suchergebnissen auf (SQlite Fehler, nur Raspian)</li>
  <li>Songs, die Umlaute enthalten, werden nicht gefunden, können teilweise nicht abgespielt werden und blockieren dann die Playliste (Charset Fehler, NTFS auf Raspian)</li>
  <li>Manche Songs werden nicht abgespielt, es werden falsche Titel angezeigt oder die Laufzeit stimmt nicht. (Schlechte MP3s, Datenbank aufräumen)
</ul> 
</p>

<p>&nbsp;</p>
 
<p align="center">
<b>Proudly presented by <br/>Arne W&ouml;rheide<br>DL1XY</b>  
</p>
<jsp:include page="/footer.jsp" />