# Projekt: Battle Royal Tetris (Fr/2, Kr)

Name & Praktikumstermin: Jonas Reitz, 5240409 (Fr/1, Kr)

## Inhaltsverzeichnis
[Kurzbeschreibung inkl Screenshot](#Kurzbeschreibung-inkl-Screenshot)  
[Beschreibung des Projektaufbaus](#Beschreibung-des-Projektaufbaus)  
[WebApi](#Dokumentation-des-implementierten-WebAPIs)  
[Interface](#Dokumentation-des-interfaces)  
[Technischer Anspruch und Features](#Technischer-Anspruch-und-Umsetzung-der-Features)  
[Quellen](#Quellennachweis)  
[Sonstiges](#Sonstiges)  

## Kurzbeschreibung inkl Screenshot

>Kurzbeschreibung
Für den kleinen Zeitvertreib für zwischendurch oder doch für die große Herausforderung für Profis.
Mein Projekt "Battle Royal Tetris" ist für alle Altersgruppen gedacht. Es verbindet das moderne Battle Royal Genre bei dem mehrere Gegner jeder für sich gegeneinander spielen mit dem Klassiker Tetris.
Das Spiel funktioniert genau wie Tetris und mit den Pfeiltasten lassen sich die Steine bewegen und auch drehen. Sobald ein Spieler eine Reihe vervollständigt erscheinen volle Reihen bei den anderen Spielern.
Wer als letztes noch im Spiel ist gewinnt!
Um Spielen zu können muss zunächst über das Registrierungsformular ein Benutzer erstellt werden, welcher sich dann mit den entsprechend richtigen Daten im Login Menü anmelden kann.
Man betritt direkt die Lobby und wartet nun das die entsprechende Anzahl an Spielern beitritt und das Spiel dann automatisch startet.

![Screenshot](screenshot.PNG)

**Hinweise**: _Die Anzahl der Spieler lässt sich über die_ `settings.txt` _in dem "/res" Ordner einstellen. Bei einem Spieler bitte_ `debugmode = true` _setzen, da ansonsten einzelne Spieler direkt gewonnen hat._
Weitere Hinweise bei [Sonstiges](#Sonstiges)

## Beschreibung des Projektaufbaus

### Abgabedateien (LOC)

Verlinkter Dateiname | Dateiart | LOC
---------------------|----------|-----
[bootstrap.css](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/css/bootstrap.css) |CSS|8832
[jquery-3.4.1.js](src/main/resources/public/js/lib/jquery-3.4.1.js) |Javascript|6801
[bootstrap.bundle.js](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/js/bootstrap.bundle.js ) |Javascript|4493
[bootstrap-grid.css](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/css/bootstrap-grid.css) |CSS|3511
[bootstrap.js](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/js/bootstrap.js ) |Javascript|3262
[bootstrap-reboot.css](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/css/bootstrap-reboot.css ) |CSS| 267
[Board.java](src/main/java/game/Board.java ) |Java|181
[Lobby.java](src/main/java/game/Lobby.java ) |Java|112
[App.java](src/main/java/main/App.java ) |Java|97
[Shape.java](src/main/java/game/Shape.java ) |Java|95
[DatabaseManager.java](src/main/java/main/DatabaseManager.java ) |Java|68
[LogUI.java](src/main/java/main/LogUI.java ) |Java|65
[ShapePrefab.java](src/main/java/game/ShapePrefab.java ) |Java|58
[game.js](src/main/resources/public/js/game.js ) |Javascript|53
[style.css](src/main/resources/public/style.css ) |CSS|46
[Settings.java](src/main/java/main/Settings.java ) |Java|26
[index.html](src/main/resources/public/index.html ) |HTML|25
[register.html](src/main/resources/public/register.html ) |HTML|21
[Javascript.js](src/main/resources/public/js/Javascript.js ) |Javascript|21
[login.js](src/main/resources/public/js/login.js ) |Javascript|18
[login.html](src/main/resources/public/login.html ) |HTML|17
[register.js](src/main/resources/public/js/register.js ) |Javascript|14
[DatabaseException.java](src/main/java/main/DatabaseException.java ) |Java|12
[PacketProcessor.java](src/main/java/PacketProcessors/PacketProcessor.java ) |Java|5
[game.html](src/main/resources/public/game.html ) |HTML|5
[bootstrap.min.css](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/css/bootstrap.min.css ) |CSS|1
[bootstrap-reboot.min.css](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/css/bootstrap-reboot.min.css ) |CSS|1
[bootstrap.min.js](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/js/bootstrap.min.js ) |Javascript| 1
[jquery-3.4.1.min.js](src/main/resources/public/js/lib/jquery-3.4.1.min.js ) |Javascript|1
[bootstrap-grid.min.css](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/css/bootstrap-grid.min.css ) |CSS|1
[bootstrap.bundle.min.js](src/main/resources/public/js/lib/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js ) |Javascript|1

Außerdem hier noch die Screenshots meiner LOC Zählung erstmal mit den Libraries:
![Screenshot](withLib.PNG)  

und hier ohne :  
![Screenshot](withoutLib.PNG)

Fall Sie diese Zählung wiederholen wollen entfernen Sie bitte den `src/resources/public/js/lib` Ordner, da er die Dateien von Bootstrap und JQuery enthält und zum offline arbeiten notwendig waren.

### Testdateien (TST)
Verlinkter Dateiname | Testart | Anzahl der Tests
---------------------|---------|-----------------
[JUnitTest.java](src/test/java/JUnitTest.java ) | JUnit5 | 21

Die Tests werden wie folgt ausgeführt:
Mit `Gradle test` in dem Hauptverzeichnis lassen sich alles Tests automatisch ausführen.

### Aufbau der Anwendung

Zu Beginn werden [PacketProcessors](src/main/java/PacketProcessors/PacketProcessor.java), nach der Initialisierung der Javalin Objektes in der [App.java](src/main/java/main/App.java) Datei erstellt.
Diese werden zusammen mit einem Schlüssel String in eine `Map<String,PacketProcessor&gt;` hinzugefügt. Dieser Schlüssel ist ein Tag Attribut eines JSON Objektes, welches in jeder Request des Clients enthalten ist.
Zum Beispiel wird die Login Request `{"tag:":"login"}` beinhalten, sodass der Server mit diesem Tag den dazugehörigen [PacketProcessors](src/main/java/PacketProcessors/PacketProcessor.java) in der Map finden kann und die dazugehörige Methode `JSONObject process(JSONObject obj)` aufrufen kann. Diese benötigt das Request JSON Objekt und liefert das result Objekt zurück.
`app.post("daten", ctx -&gt; {
try {
ctx.result(processEvent( URLDecoder.decode(ctx.queryString(), StandardCharsets.UTF_8.toString())));
}catch(Exception e) {
LogUI.print(e);
}`

Diese Snippet zeigt, wie dem Javalin Objekt ein neuer PostEventHandler hinzugefügt wird, welcher das JSON Objekt decoded und damit die `String processEvent(String decode)` Methode aufruft und den String im JSON Format als result setzt.

Zu Beginn werden außerdem auch die [Spielsteine](src/main/java/game/ShapePrefab.java) aus Textdateien im [res](res) Ordner geladen. Diese Textdateien beinhalten die mögliche Drehungen der Spielsteine in einem 4x4 Raster, wobei der Stein als 1 und Luft als 0 dargestellt wird.

Loggt sich ein neuer Spieler ein, so wird er automatisch der [Lobby](src/main/java/game/Lobby.java) zugewiesen und es wird ein [Board](src/main/java/game/Board.java) für ihn erstellt. Außerdem erhält er eine SessionId die lokal auf dem Client zwischengespeichert wird. Sobald die Anzahl der benötigten Spieler erreicht ist, startet das Spiel(Lobby) und es starten auch die einzelnen Boards. Sowohl die Lobby implementieren das Runnable interface, und eine Start und Stop Methode. Die Start Methode erstellt zudem einen neuen Thread und startet diesen.

Der Spieler fragt in einem Intervall in der [game.js](src/main/resources/public/js/game.js) sein momentanes Spielbrett von dem Server ab. Die `sendRequest(request,callback)` funktion wird in dem Fall mit `{"tag" : "getCurrentBoard","id":sessionId}` aufgerufen und der `render` funktion als Callback. Die `render` Funktion zeichnet nun das momentane Spielfeld auf dem [game.html](src/main/resources/public/game.html) canvas.
Sollte der Spieler nun eine Taste drücken [game.js 54-61](src/main/resources/public/js/game.js), wird eine Request an den Server gestellt und Dieser prüft nun in der [canMove](src/main/java/game/Shape.java)(16-28) Methode, ob diese Bewegung des [Spielsteins](src/main/java/game/Shape.java) möglich war.
Sollte ein Spieler nun eine Reihe vervollständigen (siehe [Board.java](src/main/java/game/Board.java)(171-180)), so wird eine neue Reihe bei den anderen Spielern erstellt.
Das Spiel endet, sobald alle bis auf einen Spieler game over [Lobby.java](src/main/java/game/Lobby.java)(80-84) sind.

Die Website hingegen ist mit Bootstrap und JQuery aufgebaut. So wird alles mit JQuerys `$().load();` Methode in den `container.content`
der [index.html](src/main/resources/public/index.html) nachgeladen.

Die Login/Registrierungsvorgänge finden über eine SQLite Datenbank statt, welche in das Projekt mit eingebunden ist und die Daten in der [brtetris.db](sql/brtetris.db) speichert.
Die Zugriffe auf die Datenbank erfolgen alle in der [Databasemanager.java](src/main/java/main/DatabaseManager.java) Datei. Zunächst muss jedoch eine Verbindung aufgebaut werden, welches in der App main Methode geschieht.

Es gibt außerdem ein Logfile Fenster, welches Errors und Datenbank anfragen ausgibt. Man kann es problemlos schließen und es öffnet sich wieder, sobald ein neuer Eintrag im Fenster entsteht.

## Dokumentation des implementierten WebAPIs

Zum Senden von Paketen der Website wird nur eine Funktion genutzt, welche sich in der Datei `Javascript.js` befindet und welche in der `index.html` geladen wird.
Die funktion `sendRequest()` nutzt zwei Parameter. Der Erste ist das JSON-Objekt welches zum Server gesendet werden soll. 
Das zweite ist eine Callback funktion, welche mit der Antwort vom Server als Parameter aufgerufen wird.

Sowohl die Serverseitigen als auch die Clientseitigen Pakete sind alle Strings im JSON Format und beinhalten alle ein `tag:String` Attribut, welches für die Identifikation des
Paketes zuständig ist.
Der Server ruft mit dem Objekt die `processEvent()` Methode auf welche eine Instanz eines Funktionalen Interfaces in der Map `Map<String,PacketProcessor&gt; processors` mithilfe des Tag attributs sucht,
und die Methode `JSONObject process(JSONObject obj)` des PacketProcessors mit der Request aufruft und die ensprechende Response and den Client zurückgibt.

Die Clientseitigen Pakete sind :

`{tag:`input`,key:String}` wobei key rotate,down,left,right oder drop sein kann. Es wird bei jedem keyPressEvent gesendet, wenn die ensprechende Taste eine Funktion hat.  
`{tag:`getCurrentBoard`,id:int}` wobei der Integer die SessionId des users ist und so das dazugehörige Spielbrett abfragt. Dies wird in der `game.js` in der `setInterval` Funktion abgefragt.  
`{tag:`login`,email:String,password:String}` wobei die Email und das Passwort schlüssel für den Benutzer sind, welche in der Datenbank abgefragt werden. Dies wird mit dem Login Button aufgerufen.  
`{tag:`register`,email:String,password:String,username:String}` wobei die Email und das Passwort Schlüssel für den Benutzer sind und der username der Anzeigename. Dies wird mit dem Registrieungs Button aufgerufen.  

Die Antworten vom Server sehen ähnlich aus:

Antwort auf `input` : `{tag:`input`,success:boolean}` wobei der success Wert angibt ob der ausgeführte Zug erlaubt war oder nicht.  
Antwort auf `getCurrentBoard` : `{tag:`board`,started:boolean,rows:[][],gameOver:boolean,isWon:boolean,playersAlive:int}` wobei started angibt ob das Spiel gestartet ist,rows ist ein multidimensionales array welches die Steine des Feldes enhält, gameover gibt an, ob der spieler verloren hat,isWon ob der Spieler gewonnen hat und playersAlive wie viele Spieler noch im Spiel sind.  
Antwort auf `login` : `{tag:`login`,success:boolean}` wobei der success Wert angibt ob die Anmeldung erfolgreich war oder nicht.  
Antwort auf `register` : `{tag:`register`,success:boolean}` wobei der success Wert angibt ob die Regestrierung erfolgreich war oder nicht.

## Dokumentation des Interfaces

Die Dokumentation des Interfaces ist in der ausführlichen [Aufbau der Anwendung](#Aufbau-der-Anwendung) mit beinhaltet, da Diese bereits den Aufbau und Ablauf des Projektes mit beinhaltet.
Zum Aufbau der Schnittstelle wird außerdem auch in dem [Aufbau der Anwendung](#Aufbau-der-Anwendung) und natürlich in der [ Dokumentation des implementierten WebAPIs](#Dokumentation-des-implementierten-WebAPIs) hingewiesen.

## Technischer Anspruch und Umsetzung der Features

Ich habe folgende Features verwendet. Die verlinkte Datei zeigt beispielhaft den Einsatz dieses Features in den angegebenen Zeilen im Quellcode.

1.Das Speichern und Laden von Daten aus mehreren Textdateien für die Einstellung des Spiels und das Laden der verschiedenen Spielsteine, [ShapePrefab.java](src/main/java/game/ShapePrefab.java) (30-45)  
2. Bootstrap und JQuery für den Allgemeinen Aufbau der Website, [index.html](src/main/resources/public/index.html) (9-30)  
3. Das JSON Datenformat für die Übertragung der Daten zwischen Client und Server. Dafür habe ich außederdem die Bibliothek org.json für Java genutzt, [Javascript.js](src/main/resources/public/js/Javascript.js) (1-10) [App.java](src/main/java/main/App.java) (32-84)  
4. Das Nutzen einer Datenbank für ein geeignetes Login/Registrieungssystem im Backend, [App.java](src/main/java/main/DatabaseManager.java) (19-103)  

Die Features werden bereits in [Beschreibung des Projektaufbaus](#Beschreibung-des-Projektaufbaus) und in [WebApi](#Dokumentation-des-implementierten-WebAPIs)(Verwendung von JSON) erläutert.

## Quellennachweis

Als Quellen hab ich hauptsächlich das Gruppenprojekt genutzt und mir den von mir geschriebenen Datenbank Code kopiert und an das Projekt angepasst habe.
Des Weiteren habe ich Teile unseres CSS und HTML Codes für den Grundaufbau der Website genutzt.

Hiermit bestätige ich auch, das die oben genannten Quellen die einzigen sind, die ich genutzt habe und der restliche Code alleine von mir geschrieben wurde.

## Sonstiges

Für die LOC Zählung bitte nur den src Ordner zählen lassen und die Libraries in src/main/rescources/public/js/lib weglassen.
Für Testzwecke lässt sich außerdem die settings.txt in dem res Ordner bearbeiten und so die Spieleranzahl in einer Lobby verändern. Falls nur ein Spieler spielen soll, bitte auch den DebugMode auf true ändern da ansonsten das Spiel direkt vorbei ist.

Des Weiteren bin ich durch die gegeben Vorschriften der LOC an meine Grenzen gestoßen, da ich für das Projekt weitere Dinge geplant hatte die durch die Beschränkung nicht umsetzbar waren.
Ich hätte gerne ein Lobby System gemacht, in dem man sich aussuchen kann, in welche Lobby man gehen möchte, in der die Spielerzahl variiert. Außerdem sollte es eine Rangliste geben, in dem sich die Spieler anhand ihrer Siege messen können
und auch Spieler mit mehr Siegen gegeneinander spielen und so ein gewisses Rating System einzubauen.
Außerdem sollten die Benutzernamen und der momentane Feld der gegnerischen Spieler auch angezeigt werden.
Ich hätte zusätzlich noch gerne Animationen und Sounds auf der Website eingebunden, jedoch war durfte der Javascript anteil ja nur bei <10% liegen.
Es hätte außerdem auch verschiedene Schwierigkeits Stufen an Bots gegeben, die die Lobbys nach eine bestimmten Zeit aufgefüllt hätten, wenn diese nicht voll gewesen wäre.
Außerdem hätte ich gerne den Cookie Store genutzt, damit die Spieler auch nach dem Schließen der Website eingeloggt blieben.

Das Spiel funktioniert super in mehreren Tabs, so das man für Testzwecke sich einfach mehrmals anmelden kann und eine Lobby fällt.
Getestete Browser sind Chrome und Firefox, wobei andere Browser eigentlich kein Problem sein dürften da keine all zu speziellen Features eines Browsers genutzt wurden.
