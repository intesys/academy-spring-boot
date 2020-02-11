## Lezione 1 - Introduzione a Spring

### Setup Ambiente

#### Aprire il progetto con Intellij

- Da terminale `git clone git clone git@github.com:intesys/academy-spring-boot.git`
- Aprire Intellij e andare su `File > New > Project from Existing Sources`
- Nella finestra che si apre selezionare `Import project from external model` e selezionare Maven.
- Impostare le il keymap di Eclipse premere SHIFT 2 volte e cercare `keymap`, selezionare `Eclipse`

#### Setup Intellij con JDK 11

- Aprire `File > Project Structure` 
- selezionare Project SDK: 11 (se SDK non presente aggiungerla cliccando su `New > JDK` e selezionare la cartella di installazione di AdoptOpenJDK) 
- Impostare project Language Level a 11 

### Lanciare l'applicazione SPring Boot

- Aprire il file `SimpleApp.java`
- Lanciare l'applicazione cliccando sul button verde a sinistra del methodo main.
- aprire il browser all'indirizzo http://localhost:8080/patient?search=Mari

### Esercizio

Fare il checkout del progetto al tag `IASB#1-START`:

```
git checkout tags/IASB#1-START
```

Ad ogni task corrisponde un commit che inizia con **IASB#1**. Per vedere la lista dei commit e quindi le modifiche da fare, cliccare qui:
https://github.com/intesys/academy-spring-boot/commits/master

#### Migliorare codice Java
- Usare un'istanza statica del DataSource
- Usare la Dependency Injection per iniettare PatientDao in PatientService
- Fix dei test utilizzando Mockito

#### Creare Applicazione Spring
- Modificare il pom.xml per includere spring core e spring context
- Aggiungere l'annotation @Config alla classe AppConfig e trasformare le property e il datasource in `@Bean`
- Creare due @Bean per PatientService e PatientDao
- Modificare il main method di SimpleApp perché crei un application context e ottenga PatientService dall'application context

### Migliorare Applicazione Spring
- Aggiungere `@ComponentScan` alla classe AppConfig. Rimuovere i due `@Bean` di patientService e patientDao. Aggiungere `@Component` alle classi `PatientService` e `PatientDao`
- Aggiungere l'annotation `@PropertySource` a AppConfig e usare la classe di spring `Environment` per creare il datasource
- modificare il pom.xml per includere spring-jdbc e usare `JdbcTemplate` invece di `DataSource`


#### Creare Applicazione Spring Boot
- modificare il pom e aggiungere il tag xml parent `spring-boot-starter-parent`. Aggiungere i vari starters (jdbc, logging, test).
- eliminare la classe AppConfig
- cambiare le property di `application.properties` e usare il prefisso `spring.datasource`


