# Lezione 6 - Spring Data JPA e Hibernate

## Setup

1. Aggiornare il codice facendo un git fetch dalla directory del repository 
   ```
   git fetch
   ```
1. Spostarsi sul commit di inizio lezione:
   ```
   git checkout :/'IASB#6: Start'
   ```

## Esercizio 1 - Trasformare i model in entities

Le classi `Patient` e `Examination` sono attualmente dei POJO. Devono essere trasformate in JPA Entity.

1. Aggiungere l'annotazione `@Entity` su entrambe le classi
2. Per ogni campo delle classi associato ad una colonna della relativa tabella aggiungere l'annotation `@Column` come segue:
   ```
   @Column(name = "<nome_della_colonna_in_uppercase>")
   ```
   In questo modo andiamo ad esplicitare il nome di ogni colonna sul database.
3. Aggiungere al campo `id` le seguenti annotazioni
   ```
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   ```
4. Aggiungere alla classe `Patient` il nuovo campo `examinations`, definito come segue
   ```
   @OneToMany(mappedBy = "patientId")
   private List<Examination> examinations = new LinkedList<>();
   ``` 
   Aggiungere anche i relativi _getter_ e _setter_:
   ```
   public void setExaminations(List<Examination> examinations) {
     this.examinations = examinations;
   }
   public List<Examination> getExaminations() {
     return examinations;
   }
   ```

## Esercizio 2 - Utilizzare EntityManager invece di JdbcTemplate

1. Farsi passare con la _dependency injection_ l'oggetto `EntityManager` nelle classi `PatientJdcbDao` e `ExaminationJdbcDao`. 
   Ad esempio:
   ```
   private final EntityManager entityManager;
   public ExaminationJdbcDao(EntityManager entityManager) {
     this.entityManager = entityManager;
   }
   ```
2. Per ogni query di tipo `SELECT` utilizzare il metodo `EntityManager.createQuery()` per creare una `TypedQuery`, passando come primo parametro la query in HQL.
   Ad esempio, la query
   ```
   return jdbcTemplate.queryForObject(
                   "select id, firstName, lastName, birthDate, fiscalCode from patient where id = :patientId",
                   Map.of("patientId", patientId),
                   new BeanPropertyRowMapper<>(Patient.class));
   ```
   diventa
   ```
   TypedQuery<Patient> query = entityManager.createQuery("select p from Patient p where p.id = :id", Patient.class);
   query.setParameter("id", patientId);
   return query.getSingleResult();
   ```
   
   Le query sono:
   
   | Nativa | HQL |
   |---|---|
   |`SELECT id, firstName, lastName, birthDate, fiscalCode FROM patient WHERE id = :patientId` | `select p from Patient p where p.id = :id` |
   |`SELECT id, firstName, lastName, birthDate, fiscalCode FROM patient WHERE lastName like :searchString or firstName like :searchString` |`select p from Patient p where p.lastName like :searchString or p.firstName like :searchString`|
   | `SELECT COUNT(*) FROM patient` | `select count(p) FROM Patient p` |
   | `SELECT * FROM examination WHERE patientId = ? ORDER BY examinationDate` | `select e from Examination e where e.patientId = :patientId order by e.examinationDate` |

3. Per ogni query di tipo `INSERT` utilizzare il metodo `EntityManager.save()` passando l'intera entità come parametro.

## Esercizio 3 - Definire JpaRepository e utilizzarle al posto dell'EntityManager

1. Creare un'interfaccia `ExaminationRepository` che estenda `JpaRepository<Examination, Long>` e aggiungere il metodo
   ```
   @Query("select e from Examination e where e.patientId = :patientId order by e.examinationDate")
   List<Examination> findByPatientId(int patientId);
   ```
2. Creare un'interfaccia `PatientRepository` che estenda `JpaRepository<Patient, Long>` e aggiungere il metodo
   ```
   @Query("select p from Patient p where p.lastName like :searchString or p.firstName like :searchString")
   List<Patient> searchPatient(String searchString);
   ```
3. Farsi passare con la _dependency injection_ le `JpaRepository` appena creati nei corrispettivi `JdbcDao`.
4. Utilizzare i metodi le `JpaRepository` anziché invocare l'`EntityManager`  

## Esercizio 4 - Usare naming convention per i metodi definiti nelle JpaRepository
È possibile utilizzare naming convention per generare automaticamente le query, senza quindi utilizzare `@Query`.
Le parole chiave da utilizzare sono qui: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

1. Nella classe `ExaminationRepository` rinominare il metodo `findByPatientId` in `findByPatientIdOrderByExaminationDate` e togliere l'annotazione `@Query`
2. Nella classe `PatientRepository` rinominare il metodo `searchPatient` in `findByLastNameContainingOrFirstNameContaining`, aggiungere un secondo parametro e togliere l'annotazione `@Query`

## Esercizio 5 - Usare la relazione OneToMany da Patient a Examination
Nella classe `ExaminationController` attualmente vengono fatte due query: una per recuperare il `Patient` a partire dal suo id e una per recuperare le `Examination`s del paziente.
Utilizzando la relazione `@OneToMany` è possibile semplificare e fare un'unica query.

1. Chiamare `patientService.getPatient(patientId)` per ottenere il `Patient`
2. Recuperare gli esami associati al paziente semplicemente  con `patient.getExaminations()` 
 
## Esercizio 6 - Impostare la EAGER fetch type
Nei log dell'applicazione notate come la chiamata a `patient.getExaminations()` generi di fatto una query separata.
Questo perché di default le relazioni `@OneToMany` sono _lazy_, cioè i dati in essa contenuti sono recuperati al primo accesso alla _Collection_.

È possibile specificare ad Hibernate una modalità _eager_, con la quale tutti i dati vengono recuperati assieme all'entity di partenza.

1.  Nella classe `Patient`, all'annotazione `@OneToMany` aggiungere `fetch = FetchType.EAGER`.

Dopo questa modifica nei log si vedrà una sola query eseguita, in corrispondenza della chiamata a `patientService.getPatient(patientId)`.
