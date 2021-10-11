# Installation de pianorgue

## Build

Pour construire le binaire : 

```sh
cd application/
mvn clean package
```

L'application se trouve `application/target/AMT-Test-.....jar`


## Installation

 - Dépends du java runtime environment. Sous Ubuntu et Debian, installer le paquet `default-jre-headless`

 - Copier l'application (`application/target/AMT-Test-.....jar`) sur le serveur

 - Installer le service `pianorgue.service` et adapter le chemin `ExecStart=` et l'utilisateur lancant l'application `User=`



