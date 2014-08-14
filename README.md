AkadVH
======

AkadVH Java Library und Console + GUI Client zum bequemen Abrufen der Noten

<b>ACHTUNG: Die AkadVH ist nicht mehr erreichbar, dieser Code funktioniert somit nicht mehr. </b>

Evtl. ist https://github.com/derdanu/akad-campus nützlich.

GUI
==

```
java -jar akadvh.jar
java -jar akadvh.jar -u benutzername -p passwort
```

Console
=======

```
usage: java -jar akadvh.jar
 -c,--console       Consolenmodus
 -h,--help          Hilfe
 -m,--modul <arg>   Modul
 -n,--noten         Notenuebersicht erstellen
 -p,--pass <arg>    Passwort
 -t,--termin        Terminuebersicht (angemeldete Module) downloaden
 -u,--user <arg>    Benutzername
 -v,--verbose       Mehr Ausgabe
 -version           Version

```

Ant Build Distribution
======================

```
ant dist
```

Proxy Support
=============
```
java -Dhttps.proxyHost=192.168.168.1 -Dhttps.proxyPort=8080 -jar akadvh.jar
```



