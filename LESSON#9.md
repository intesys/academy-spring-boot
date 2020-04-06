# Lezione 9 - jHipster

## Setup

1. Aggiornare il codice facendo un git fetch dalla directory del repository 
   ```
   git fetch
   ```
1. Spostarsi sul commit di inizio lezione:
   ```
   git checkout :/'IASB9: Start'
   ```

## [Installazione jHipster](https://www.jhipster.tech/installation/) 
Quick setup
* Install Java 11. We recommend you use AdoptOpenJDK builds, as they are open source and free.
* Install Node.js from the [Node.js website](https://nodejs.org/en/download/) (please use an LTS 64-bit version, non-LTS versions are not supported)
* Install JHipster: npm install -g generator-jhipster
*(optional) If you want to use a module or a blueprint (for instance from the JHipster Marketplace), install Yeoman: npm install -g yo

# Esercizi

## Esercizio 1 [Creazione progetto](https://www.jhipster.tech/creating-an-app/)
* Creare una nuova cartella `academy-jhipster`
* Eseguire il comando `jhipster` con le seguenti configurazioni:

   ```
    * Which *type* of application would you like to create? Monolithic application (recommended for simple projects)
    * [Alpha] Do you want to make it reactive with Spring WebFlux? No
    * What is the base name of your application? SpringBootAcademy
    * What is your default Java package name? it.intesys.academy
    * Do you want to use the JHipster Registry to configure, monitor and scale your application? No
    * Which *type* of authentication would you like to use? JWT authentication (stateless, with a token)
    * Which *type* of database would you like to use? SQL (H2, MySQL, MariaDB, PostgreSQL, Oracle, MSSQL)
    * Which *production* database would you like to use? MySQL
    * Which *development* database would you like to use? H2 with in-memory persistence
    * Do you want to use the Spring cache abstraction? Yes, with the Ehcache implementation (local cache, for a single node)
    * Do you want to use Hibernate 2nd level cache? Yes
    * Would you like to use Maven or Gradle for building the backend? Maven
    * Which other technologies would you like to use? API first development using OpenAPI-generator
    * Which *Framework* would you like to use for the client? Angular
    * Would you like to use a Bootswatch theme (https://bootswatch.com/)? Default JHipster
    * Would you like to enable internationalization support? Yes
    * Please choose the native language of the application Italian
    * Please choose additional languages to install English
    * Besides JUnit and Jest, which testing frameworks would you like to use? (Press <space> to select, <a> to toggle all, <i> to invert selection)
    * Would you like to install other generators from the JHipster Marketplace? No
   ```
 
Alla fine della generazione e dell'installazione comparirà questo messaggio
 
 ```
Application successfully committed to Git from C:\Users\ecostanzi\dev\projects\intesys\academy\academy-jhipster.

If you find JHipster useful consider sponsoring the project https://www.jhipster.tech/sponsors/

Server application generated successfully.

Run your Spring Boot application:
./mvnw (mvnw if using Windows Command Prompt)

Client application generated successfully.

Start your Webpack development server with:
 npm start


> hipster-academy@0.0.1-SNAPSHOT cleanup C:\Users\ecostanzi\dev\projects\intesys\academy\academy-jhipster
> rimraf target/classes/static/ target/classes/aot

INFO! Congratulations, JHipster execution is complete!
```
 


## Esercizio 2

Lanciare il frontend del progetto jhipster:

```bash
npm start
```

Lanciare il backend del progetto jhipster:

```properties
./mvnw
```

In alternativa è possibile lanciare il backend dalla main class del progetto Spring Boot

## Esercizio 3

Spegnere il backend jhipster.

Copiare nella cartella del progetto jhipster il file `jhipster-academy.jdl` di questo repository.

```
jhipster import-jdl jhipster-academy.jdl
```

## Esercizio 4

Customizzare le property:

- abilitare l'h2 console nel file `application-dev.yml`
- cambiare il file `application-prod.yml` perche' punti al database mysql

## Esercizio 5

Modificare la entity Examination perché l'utente non venga inserito dalla pagina di Frontend, ma venga
gestito dalla class `ExaminationService` generata da jhipster.

Hints:
- Usare la classe statica `SecurityUtil.getCurrentUserLogin()` per ottenere lo username dell'utente e il 
repository UserRepository per leggere l'utente dal database.
- Modificare il file angulare di frontend `examination.component.html` e togliere dalla
form la parte relativa all'utente



## Esercizio 6

Modificare le entity Examination e Patient perché la data di creazione e aggiornamento vengano gestite dai rispettivi
service e non dal Frontend

## Esercizio 7

- Copiare il contenuto del file `api.yml` di questo progetto nel file `src/main/resources/swagger/api.yml`  del progetto
jhipster. 
- Spegnere il backend, lanciare il comando `./mvnw compile` per generare i delegate openapi.
- Implementare le interfacce ExaminationApiDelegateImpl e PatientApiDelegateImpl (che implementano le classi generate)
e usare le classi generate da jhipster per implementare le API

## Esercizio 8

* Customizzare view lista pazienti aggiungendo la ricerca

## Esercizio 9

* Riportare la ChaosMonkey nel progetto jhipster

