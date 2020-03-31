# Lezione 8 - Spring Boot Testing

## Setup

1. Aggiornare il codice facendo un git fetch dalla directory del repository 
   ```
   git fetch
   ```
1. Spostarsi sul commit di inizio lezione:
   ```
   git checkout :/'IASB8: Start'
   ```

## Esercizio 1 - Unit test

Scrivere il test `getBMI`. Il metodo `assertThat` deve essere importato da `org.assertj.core.api.Assertions`

```java
@Test
@DisplayName("test bmi calculation")
//1.95, 85 -> 22.35
void getBMI() {
    double bmi = BMICalculator.calculateBMI(85d, 1.95d);
    assertThat(bmi).isEqualTo(22.35d);
}
``` 

---

Scrivere il test per `testNegativeBMI`, `Assertions.assertThrows(IllegalArgumentException.class, ()-> //invocare metodo getBmiCondition con valore negativo);`

---

Scrivere il test per `testBmiCondition`. Aggiungere qualche altra condizione all'annotation `CsvImport`.

```java
@DisplayName("Calculates multiple BMI conditions")
@ParameterizedTest(name = "a BMI of {0} is categorized as {1}")
@CsvSource({ "12.2, UNDERWEIGHT", "20, NORMAL", "30, OBESE"})
void testBmiCondition(double bmi, BMICalculator.BMICondition bmiCondition) {
    assertThat(BMICalculator.getBMICondition(bmi)).isEqualTo(bmiCondition);
}
```

## Esercizio 2 - Unit Test con Mockito

Scrivere il test per `PatientServiceTest.shouldReturnPatient` utilizzando mockito, completare la parte di assertions.

```java
@Test
@DisplayName("Returns a patient by id")
public void shouldReturnPatient() {
    patientDao = mock(PatientDao.class);
    when(patientDao.findById(1L))
            .thenReturn(newPatient(1L, "Mario", "Rossi"));

    PatientService patientService = new PatientService(patientDao);
    Patient patient = patientService.getPatient(1L);
    //completare la parte di assertions
}
```

---

Scrivere il test `PatientServiceTest.shouldThrowException`, programmare mockito perche' ritorni un paziente `null` e testare l'eccezzione con
asserThrows (vedi es. 1)

---

Annotare l'intera classe `PatientServiceTest` con `@ExtendWith(MockitoExtension.class)`, creare i mock utilizzando l'annotation `Mock`
su PatientDao. Costruire l'istanza da testare (`patientservice`) in un metodo annotato con `@BeforeEach`.

---

Scrivere i test di `ExaminationServiceTest`, la struttura della classe è simile a PatientServiceTest, ma la dipenenza
`NotificationService` deve essere annotata come `@Spy`. ExaminationDao invece è un mock.

```java
@DisplayName("send notification when patient examination BMI is critical")
@Test
public void testBmiNotification() {
    //given
    Examination examination = new Examination();
    examination.setHeight(195);
    examination.setWeight(200);

    //test
    examinationService.save(examination);

    //verify
    verify(notificationService, times(1)).sendCriticalExaminationEvent(examination);
}
```

---

Scrivere il test `testBmiNotificationNotSent`, la notifica non deve essere inviata se il BMI del paziente non è critico.

## Esercizio 3 - Integration Tests con @SpringBootTest

Annotare la classe `PatientServiceIntTest` con `@SpringBootTest` e iniettare con `@Autowired` le class `PatientService` 
e `PatientRepository`. Completare il test

```java
@Test
@Transactional
@DisplayName("Case sensitive search works with patient in the database")
void testPatientSearch() {
    //given
    //salvo i pazienti nel db con PatientRepository

    //test
    //cerco pazienti nel db via patientService passando una stringa

    //assert
   //controllo che la ricerca ritorni dei risultati validi
    
}
```

---

Testare `DashboardController` in `DashboardControllerTest`. Annotare la classe con 
`@SpringBootTest`, `@AutoConfigureMockMvc`, `@Transactional`.  Scrivere il primo test:

```java
@Autowired
MockMvc mockMvc;

@Test
@DisplayName("Dashboard home shows 0 patients")
void testHomePage() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("0 Patients")));
}
```

---

Scrivere `testHomePageWithSomePatients` e controllare che la risposta contenga la stringa `3 Patients`. Usare
`PatientRepository` o `@Sql` per inserire i pazienti nel db prima di fare il test.

---

Scrivere il test `testAnonymousSearchesPatients`, testando che ritorni status `302`.

---

Scrivere il test `testAdminSearchesPatients`, testando che ritorni `200`. Usare l'annotation `@WithMockUser`.

## Esercizio 4 - Test slicing e MockBean

In `DashboardControllerTest` sostituire `@SpringBootTest` con 
`@WebMvcTest(controllers = {DashboardController.class, PatientController.class})`. Rimuovere `@Transactional` ed eventualmente `PatientRepository`. 
Lanciare il test e verificare che dia errore.

---

Inserire `@MockBean PatientService patientService;`, 
modificare `testHomePageWithSomePatients` mockando il comportamento di `PatientService`. 


## Esercizio 5 - REST Integration Tests

Scrivere i test di `PatientServiceApiIntTest`. Il primo test:

```java
@SpringBootTest
@ActiveProfiles("rest")
public class PatientServiceApiIntTest {

    @Autowired
    PatientService patientService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @DisplayName("An API search with no results returns an empty list")
    void testPatientSearchWithNoResults() {
        //given
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:8080/api/patients/search?search=Mari"))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        //test
        List<Patient> patients = patientService.searchPatient("Mari");

        //assert
        mockServer.verify();
        assertThat(patients).hasSize(0);
    }
```

---

Scrivere il secondo test `testPatientSearchWithResults`, fare in modo che non ritorni un json vuoto, ma una lista di pazienti.

Suggerimento, per creare il json usare:
```java
var patientList = List.of(newPatient("Giuseppe", "Verdi"));
String patientListResponseJson = objectMapper.writeValueAsString(patientList);
```

## Esercizio 6 - Testconainers

Inserire dipendenze nel `pom.xml`:

```java
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers</artifactId>
    <version>1.13.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mysql</artifactId>
    <version>1.13.0</version>
    <scope>test</scope>
</dependency>
```

Modificare il file `application-mysql.properties` e cambiare le righe:
```java
driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
url: jdbc:tc:mysql:8.0.19://hostname/academydb
```

Modificare il file `application.yml` perché includa il profilo mysql di default (usare `spring.profiles.include`)

---
