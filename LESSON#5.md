# Lezione 5 - Database Stuff


## Setup

1. Aggiornare il codice facendo un git fetch dalla directory del repository 
   ```
   git fetch
   ```
1. Spostarsi sul commit di inizio lezione:
   ```
   git checkout :/'IASB5 start'
   ```

### Esercizio 1 - JDBC Template Keyholder

Modificare il salvataggio dell'examination in modo da ottenere l'ID univoco dell'esame appena inserito.

--- 

Modificare il metodo `save`  ExaminationDao.

```java

Examination save(Examination examination);

``` 
Cliccare su `Build > Build Project` per far apparire gli errori in compilazione.

---

Modificare il metodo `save` di ExaminationJdbcDao.

```java
public Examination save(Examination examination) {
    logger.info("Saving examination for patient {} via JDBC Template", examination.getPatientId());
    String inserExaminationSQL =
            "INSERT INTO examination(patientId, diastolicPressure, systolicPressure, height, weight, examinationDate) VALUES (?, ?, ?, ?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(inserExaminationSQL, new String[]{"id"});
        ps.setLong(1, examination.getPatientId());
        ps.setInt(2, examination.getDiastolicPressure());
        ps.setInt(3, examination.getSystolicPressure());
        ps.setInt(4, examination.getHeight());
        ps.setInt(5, examination.getWeight());
        ps.setDate(6, new Date(Date.from(examination.getExaminationDate().toInstant()).getTime()));
        return ps;
    }, keyHolder);

    examination.setId(keyHolder.getKey().longValue());

    return examination;

}
```

---

Cliccare su `Build > Build Project` e risolvere i restanti problemi di compilazione

### Esercizio 2 - Flyway

Gestire i file sql con flyway.

---

Aggiungere la dipendenza nel `pom.xml`

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

---

Creare la cartella `db/migrations` all'interno della cartella `src/main/resources`

In `db/migrations` creare il file `V0001__create_table_patient.sql` e copiare dal file `schema.sql` la parte di create table del patient.

In `db/migrations` creare il file `V0002__create_table_examination.sql` e e copiare dal file `schema.sql` la parte di create table examination.

Eliminare il file schema.sql

Creare la cartella `db/fake-data` all'interno della cartella `src/main/resources`

Creare il file `V1001__test_data.sql`

Creare il file `src/main/resources/application-fake-data.yml`.

```yaml
spring:
  flyway:
    locations: classpath:/db/migration,classpath:/db/fake-data
```

Il profilo di spring `fake-data` verrà attivato automaticamente grazie al

Eliminare il file `DataSourceConfiguration.java`

Assicurarsi che il metodo `configure` di `SecurityConfiguration.java` contenga la parte `.headers().frameOptions().disable()`. 
Questa modifica serve a ripristinare la h2 console.

```java
@Override
protected void configure(HttpSecurity http)
    throws Exception {

    http
        .authorizeRequests()
            .antMatchers("/", "/css/**", "/js/**" , "/login", "/logout").permitAll()
            .antMatchers("/api/**").permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin()
        .and()
            .csrf().disable()
            .headers().frameOptions().disable();
}
```

Avviare l'applicazione e collegarsi `http://localhost:8080/h2-console`. Guardare il contenuto della tabella `flyway_schema_history`. 

Provare ad aggiungere colonne alle tabelle gia' esistenti. Ricordandosi che un file, una volta eseguito da flyway, non deve cambiare!

### Esercizio 3 - NamedJDBCTemplate

Sfruttare NamedPArameterJdbcTemplate. 

---

Modificare `PatientJDBCDao` e usare `NamedParameterJdbcTemplate` invece di JDBCTemplate.

```java
@Repository
class PatientJdcbDao implements PatientDao {

    private static Logger logger = LoggerFactory.getLogger(PatientJdcbDao.class);
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PatientJdcbDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ...
```

Cambiare il metodo findByPatientId:

```java
public List<Examination> findByPatientId(long patientId) {
    logger.info("Fetching patient {} examinations via JDBC template", patientId);
    return jdbcTemplate
        .query("SELECT * FROM examination WHERE patientId = :patientId ORDER BY examinationDate",
                Map.of("patientId", patientId), new BeanPropertyRowMapper<>(Examination.class));
}
```

Modificare anche searchPatient e countPatients. 

--- 

Come per PatientJdbcDao, modificare `ExaminationJDBCDao` e usare `NamedParameterJdbcTemplate` invece di JDBCTemplate.

Un esempio di insert into:

```java

public Examination save(Examination examination) {
    logger.info("Saving examination for patient {} via JDBC Template", examination.getPatientId());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String inserExaminationSQL =
            "INSERT INTO examination(patientId, diastolicPressure, systolicPressure, height, weight, examinationDate) " +
                    "VALUES (:patientId, :diastolicPressure, :systolicPressure, :height, :weight, :examinationDate)";
    jdbcTemplate.update(inserExaminationSQL, new MapSqlParameterSource(
            Map.of(
                    "patientId", examination.getPatientId(),
                    "diastolicPressure", examination.getDiastolicPressure(),
                    "systolicPressure", examination.getSystolicPressure(),
                    "height", examination.getHeight(),
                    "weight", examination.getWeight(),
                    "examinationDate", OffsetDateTime.now()
            )), keyHolder, new String[] {"id"});
    examination.setId(keyHolder.getKey().longValue());

    return examination;
}
```
