## Lezione 2 - Configurare Spring Boot

### Lanciare l'applicazione Spring Boot

- Aprire il file `SimpleApp.java`
- Lanciare l'applicazione cliccando sul button verde a sinistra del methodo main.
- aprire il browser all'indirizzo http://localhost:8080/patient?search=Mari

### Esercizi

Fare il checkout del progetto al tag `IASB#2-START`:

```
git checkout tags/IASB#2-START
```

Ad ogni task corrisponde un commit che inizia con **IASB#2**. Per vedere la lista dei commit e quindi le modifiche da fare, cliccare qui:
https://github.com/ecostanzi/intesys-academy-spring-boot/commits/master

### Banner 
Aggiungere un banner custom generato tramite sito: http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20

Verificare che la nostra nuova webapp parta con lo stile giusto!

### Properties VS YAML
Convertire il file **application.properties** in **application.yml**

### ServerProperties
1. Aggiungere una configurazione in **application.yml** per la porta di startup del applicativo;
2. Creare una @Configuration per leggere la configurazione della porta;

### @Configuration VS @EnableConfigurationProperties
Abilitare la componente ServerProperties tramite @EnableConfigurationProperties

### properties/profili uno di sviluppo e uno di produzione
1. Creare 2 file di properties/profili uno di sviluppo e uno di produzione in cui si differenziano le properties del datasource con h2 per dev;
2. Verificare le differenze facendo partire l'applicativo con i valori: prod e dev 
        
        -Dsping.profiles.active

### Profilo di default
Creare una configurazione che abiliti di default il profilo di dev;

### Custom properties
1. Aggiungere una custom properties 
2. Aggiungere una configuration per leggere il valore della custom property

### Properties Validation
1. Aggiungere @Validated alla componente responsabile di leggere le custom properties
2. Aggiungere @NotEmpty su un campo

### Profilo FAKE-DATA
Aggiungere un profilo fake-data che se attivo inizializzi il datasource con dati fittizi.
