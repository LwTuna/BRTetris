# Battle Royal Tetris
#### Inhaltverzeichnis
[Kurzbeschreibung](#Kurzbeschreibung)  
[Screenshot](#Screenshot)  
[Aufbau](#Aufbau)  
[WebApi](#WebApi)  
[Interface](#Interface)  
[Features](#Features)  
[Sonstiges](#Sonstiges)  
[Quellen](#Quellen)  


#### Kurzbeschreibung
Für den kleinen Zeitvertreib für Zwischendurch oder doch für die große Herausvorderung für Profis.  
Mein Projekt "Battle Royal Tetris" ist für alle Altersgruppen gedacht. Es verbindet das moderne Battle Royal Genre bei dem mehrer Gegner jder für sich gegeneinander spielen mit dem Klassiker Tetris.
Das Spiel funktioiert genau wie Tetris und mit den Pfeiltasten lassen sich die Steine bewegen und auch drehen. Sobald ein Spieler eine Reihe vervollständigt erscheinen volle Reihen bei den anderen Spielern.  
Wer als letzes noch im Spiel ist gewinnt!  
Um Spielen zu können muss zunächst über das Registrierungsformular ein Benutzer erstellt werden, welcher sich dann mit den entsprechend richtigen Daten im Login Menu anmelden kann.  
Man betritt direkt die Lobby und wartet nun das die entsprechende Anzahl an Spielern beitritt und das Spiel dann automatisch startet.  

_Die Anzahl der Spieler lässt sich über die settings.txt in dem "/res" Ordner einstellen. Bei einem Spieler bitte debugmode = true setzen, da ansonsten einzelne Spieler direkt gewonnen hat._  


#### Screenshot

#### Aufbau
#### Interface
#### Features

#### Sonstiges
Für die LOC Zählung bitte nur den src Ordner zählen lassen und die Libraries in srx/main/rescources/public/js/lib weglassen.  
Außerdem die Tester.java Datei dafür ignorieren. Sie ist ein alleinstehendes Programm welches automatische eine neue Instanz des Servers anlegt und testet.
Für Testzwecke lässt sich außerdem die settings.txt in dem Res Ordner bearbeiten und so die Spieleranzahl in einer Lobby verändern. Falls nur ein Spieler
spielen soll bitte auch den DebugMode auf true ändern da ansonsten das Spiel direkt vorbei ist.  

Desweitern bin ich durch die gegeben Vorschriften der LOC an meine Grenzen gestoßen, da ich für das Projekt weitere Dinge geplant hatte die durch die Beschränkung nich umsetzbar waren.
Ich hätte gerne ein Lobby System gemacht in dem man sich aussuchen kann, in welche Lobby man gehen möchte, in der die Spielerzahl variiert. Außerdem sollte es eine Rangliste geben, in dem sich die Spieler anhand ihrer Siege messen können 
und auch Spieler mit mehr Siegen gegeneinander spielen und so ein gewisses Rating System einzubauen.
Außerdem sollten die Benutzernamen und der momentane Feld der gegnerischen Spieler auch angezeigt werden.  
Ich hätte zusätlich noch gerne Animationen und Sounds auf der Website eingebunden, jedoch war durfte der Javascript anteil ja nur bei <10% liegen.  
Es hätte außerdem auch verschiedene Schwierigkeitstufen an Bots gegeben, die die Lobbys nach eine bestimmten Zeit aufgefüllt hätten, wenn diese nich voll gewesen wäre.

Das Spiel funktioniert super in mehreren Tabs, so das man für Testzwecke sich einfach mehrmals anmelden kann und eine Lobby füllt.  
Getestete Browser sind Chrome und Firefox, wobei andere Browser eigentlich kein Problem sein dürften da keine all zu Speziellen Features eines Browsers genutzt wurden.  

#### Quellen

Als Quellen hab ich hauptsächlich das Gruppenprojekt genutzt und mir den von mir geschriebenen Datenbank Code kopiert und an das Projekt angepasst habe.
Desweiteren habe ich Teile useres CSS und HTML Codes für den Grundaufbau der Website genutzt.


Hiermit bestätige ich auch, das die oben genannten Quellen die einzigen sind die ich genutzt habe und der restliche Code alleine von mir geschrieben wurde.

#### WebApi
Zum senden von Paketen der Website wird nur eine funktion genutzt, welche sich in der Datei `Javascript.js` befindet und welche in der `index.html` geladen wird.
Die funktion `sendRequest()` nutzt zwei Parameter. Der Erste ist das JSON-Objekt welches zum Server gesendet werden soll.  n
Das zweite ist eine Callback funktion, welche mit der Antwort vom Server als Parameter aufgerufen wird.

Sowohl die Serverseitigen als auch die Clientseitigen Pakete sind alle Strings im JSON Format und beinhalten alle ein `tag:String` Attribut, welches für die Indentifikation des 
Paketes zuständig ist.
Der Server ruft mit dem Objekt die `processEvent()` Methode auf welche eine Instanz eines Funktionalen Interfaces in der Map `Map<String,PacketProcessor> processors` mithilfe des Tag attributs sucht,
und die Methode `JSONObject process(JSONObject obj)` des PacketProcessors mit der Request aufruft und die ensprechende Response and den Client zurückgibt.  

Als Clientseitige Requests werden




