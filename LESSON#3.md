# Lezione 3 - Spring MVC

## Setup

1. Aggiornare il codice facendo un git fetch dalla directory del repository 
   ```
   git fetch
   ```
1. Spostarsi sul commit di inizio lezione:
   ```
   git checkout :/'IASB#3 start'
   ```

## Live coding
I seguenti punti saranno fatti durante la lezione come live coding.

### Aggiungere un contatore dei pazienti al tasto "Search Patients"
```
git checkout :/'IASB#3E1.0'
```
1. Modificare `PatientDao` implementando un metodo `countPatients()`
1. Modificare `PatientService` delegando il metodo `PatientDao.countPatients()`
1. Modificare `DashboardController` in modo da farsi iniettare un riferimento a `PatientService`
1. Utilizzare `PatientService` per ottenere il numero di pazienti e passarlo come _model attribute_
1. Modificare `index.mustache` per visualizzare il numero di pazienti

### Aggiungere la funzionalità di ricerca paziente
```
git checkout :/'IASB#3E2.0'
```
1. Creare `PatientController` ed implementare un metodo per gestire la _GET_ di visualizzazione della form di ricerca pazienti
1. Modificare `index.mustache` cambiando l'_href_ del link di ricerca paziente facendolo puntare al path gestito dal metodo implementato al punto precedente  
1. Implementare una view `patient-view.mustache` per visualizzare la form di ricerca pazienti
1. Aggiungere a `PatientController` un metodo per gestire la _POST_ di ricerca pazienti
1. Modificare `patient-view.mustache` per visualizzare i risultati della ricerca

## Esercizi

### Aggiungere la funzionalità di aggiunta esame
```
git checkout :/'IASB#3E3.0'
```
1. Spostarsi sul commit con messaggio `IASB#3 Add "add examination" feature (START)`. 
   Il codice che troverete è uno scheletro di implementazione della funzionalità, Dovrete implementare tutti i TODO nel codice.
   Una possibile soluzione è al commit successivo (`IASB#3 Add "add examination" feature (END)`).
 
### Aggiungere la funzionalità di lista esami di un paziente
```
git checkout :/'IASB#3E4.0'
```
1. Aggiungere a `ExaminationController` un metodo per gestire la _GET_ per la lista degli esami di un paziente. 
   Utilizzare un path simile a `/examinations/{patientId}` e l'annotazione `@PathVariable` per ottenere il `patientId`.
1. Modificare il metodo in `ExaminationController` che salva l'esame, facendo redirect sulla lista degli esami del paziente.
1. Implementare in `ExaminationDao` il salvataggio di un esame
1. Modificare `ExaminationService` delegando al metodo implementato al punto precedente il salvataggio dell'esame
1. Implementare la view `patient-examinations.mustache` per visualizzare la lista degli esami del paziente

### Aggiungere un tasto "visualizza esami"
```
git checkout :/'IASB#3E5.0'
```
1. Modificare `patient-view.mustache` aggiungendo ai risultati risultati della ricerca pazienti un tasto "view examinations" 
   che punta al path implementato all'esercizio precedente
