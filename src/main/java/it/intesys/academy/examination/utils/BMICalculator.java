package it.intesys.academy.examination.utils;

public class BMICalculator {

    public static double calculateBMI(double weightInKgs, double heightInMeters) {
        double bmi = weightInKgs / (Math.pow(heightInMeters, 2d));
        return Math.round(bmi * 100) / 100d;
    }

    public static BMICondition getBMICondition(double bmi) {

        if(bmi < 0) {
            throw new IllegalArgumentException("Invalid negative BMI");
        }

        for (BMICondition condition : BMICondition.values()) {
            if (bmi >= condition.getFrom() && bmi < condition.getTo()) {
                return condition;
            }
        }
        throw new IllegalStateException("Something wrong here");
    }

    public static BMICondition getBMICondition(double weightInKgs, double heightInMeters) {
        return getBMICondition(calculateBMI(weightInKgs, heightInMeters));
    }

    /**
     * Si misura dividendo il peso corporeo (in kg) per il quadrato della statura (in metri).
     * I valori soglia di IMC consigliati dall’Organizzazione Mondiale della Sanità per definire
     * la condizione di sottopeso, normopeso, sovrappeso e obesità dell’adulto sono stati recentemente unificati per uomini e donne:
     *
     * IMC inferiore a 18,5 = sottopeso
     * IMC compreso tra 18,5 e 24,9 = normopeso
     * IMC compreso tra 25 e 29,9 = sovrappeso
     * IMC compreso tra 30 e 34,9 = 1° grado di obesità
     * IMC compreso tra 35 e 39,9 = 2° grado di obesità
     * IMC maggiore di 40 = 3° grado di obesità
     */
    public enum BMICondition {

        UNDERWEIGHT(0d, 18.5d),
        NORMAL(18.5, 30),
        OVERWEIGHT(25, 30),
        OBESE(30, 35),
        EXTREMELY_OBESE(35, 1000d);

        private double from;
        private double to;

        BMICondition(double from, double to) {
            this.from = from;
            this.to = to;
        }

        public double getFrom() {
            return from;
        }

        public double getTo() {
            return to;
        }

    }
}
