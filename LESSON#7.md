# Lezione 7 - Spring AOP

## Setup

1. Aggiornare il codice facendo un git fetch dalla directory del repository 
   ```
   git fetch
   ```
1. Spostarsi sul commit di inizio lezione:
   ```
   git checkout :/'IASB#7: Start'
   ```

## Esercizio 1 - Valorizzare automaticamente colonne della tabella Examination

Nell'entity `Examination` sono state aggiunte le colonne _createDate_ e _lastModificationDate_.
In questo esercizio si creerà un aspetto che valorizza automaticamente queste colonne durante il salvataggio dell'entity.

1. Creare una nuova classe `AuditAspect` annotata con `@Component` e `@Aspect`
2. Definire un _pointcut_ che selezioni il metodo `save` di `ExaminationJdbcDao`
   ```
   @Pointcut("execution(public * it.intesys.academy.examination.ExaminationJdbcDao.save(..))")
   ```
3. Definire un _Advice_ di tipo _AfterReturning_ che utilizzi il Pointcut definito sopra e al suo interno andare a valorizzare le colonne
   ```
   @AfterReturning(pointcut = "dao()", returning = "examination")
   public void saveAuditData(Examination examination) {
       LOGGER.warn("saving audit data for " + examination);
       if (examination.getCreateDate() == null) {
           examination.setCreateDate(OffsetDateTime.now());
       }
       examination.setLastModificationDate(OffsetDateTime.now());
       examinationRepository.save(examination);
   }
   ```
## Esercizio 2 - Loggare le chiamate ai metodi

1. Creare una nuova classe `LoggingAspect` annotata con `@Component` e `@Aspect`
2. Definire un _pointcut_ che selezioni tutti i metodi dei Dao, Service e Controller
   ```
   @Pointcut("bean(*Dao) || bean(*Controller) || bean(*Service)")
   ```
3. Definire un _Advice_ di tipo _AfterThrowing_ che utilizzi il Pointcut definito sopra per loggare le eccezioni
   ```
   @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
   public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {

       LOGGER.error("Exception in {}.{}() with cause = '{}' and exception = '{}'", joinPoint.getSignature()
           .getDeclaringTypeName(),
                       joinPoint.getSignature()
                           .getName(),
                       e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
   }
   ```
4. Definire un _Advice_ di tipo _Around_ che utilizzi il Pointcut definito sopra per loggare l'entrata e l'uscita dai metodi
   ```
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint)
        throws Throwable {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature()
                .getDeclaringTypeName(),
                            joinPoint.getSignature()
                                .getName(),
                            Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature()
                    .getDeclaringTypeName(),
                                joinPoint.getSignature()
                                    .getName(),
                                result);
            }
            return result;
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature()
                .getDeclaringTypeName(),
                            joinPoint.getSignature()
                                .getName());

            throw e;
        }
    }
   ``` 
5. Aumentare il livello di logging a _DEBUG_ per l'aspetto creato
   ```
   logging:
     level:
       it.intesys.academy.aop: debug
   ```

## Esercizio 3 - Chaos Monkey

Vogliamo simulare con gli aspetti comportamenti anomali del codice. Per questo implementiamo una semplice [Chaos Monkey](https://en.wikipedia.org/wiki/Chaos_engineering#Chaos_Monkey) che:
* lancia casualmente eccezioni quando si effettuano operazioni sul database
* modifica casualmente il valore di ritorno per i metodi che ritornano _int_

1. Creare una nuova classe `ChaosMonkeyAspect` annotata con `@Component` e `@Aspect`
2. Definire un _pointcut_ che selezioni tutti i metodi delle `JpaRepository`
   ```
   @Pointcut("bean(*Dao) || bean(*Controller) || bean(*Service)")
   ```
3. Definire un _Advice_ di tipo _AfterReturning_ che definisca _inline_ un pointcut che selezioni tutti i metodi delle `JpaRepository` 
    e al suo interno lanciare casualmente un'eccezione
    ```
    @AfterReturning("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public void throwRandomException() {
        if (random.nextDouble() >= 0.6) {
            throw new RuntimeException("The monkey was here!");
        }
    }
    ```
4. Definire un _Advice_ di tipo _Around_ che definisca _inline_ un pointcut che selezioni tutti i metodi che ritornano _int_ 
   e al suo interno modificare casualmente il valore di ritorno
   ```
    @Around("execution(int it.intesys.academy..*(..))")
    public int messUp(ProceedingJoinPoint pjp)
        throws Throwable {

        int returnValue = (int) pjp.proceed();
        if (random.nextDouble() >= 0.8) {
            returnValue = random.nextInt();
        }
        return returnValue;
    }
   ```
  