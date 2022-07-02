package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static String generateDate(int months, String formatPattern) {
        return LocalDate.now().plusMonths(months).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public static String getYearWithOneDigit() {
        return DataHelper.getCVCNumber(1);
    }

    public static String getMonthWithOneDigit() {
        Faker faker = new Faker();
        return String.valueOf(faker.number().numberBetween(1, 9));
    }

    public static String getDateWithZero() {
        return "00";
    }

    public static String getNotExistMonth() {
        Faker faker = new Faker();
        return String.valueOf(faker.number().numberBetween(13, 99));
    }

    public static String getCardNumberWithZero() {
        return "0000 0000 0000 0000";
    }

    public static String getNotAlowedCardNumber() {
        return "4444 4444 4444 4443";
    }

    public static String getSixteenDigitCardNumber() {
        return "4444 4444 4444 444";
    }

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getFullUsersName() {
        Faker faker = new Faker();
        return faker.name().name().toUpperCase();
    }

    public static String getUsersNameByLength(int count) {
        Faker faker = new Faker();
        return faker.lorem().characters(count, false, false).toUpperCase();
    }

    public static String getFullUsersNameInLowCaseLetters() {
        Faker faker = new Faker();
        return faker.name().name().toLowerCase();
    }

    public static String getFullUsersNameInUpperCaseAndLowCaseLetters() {
        Faker faker = new Faker();
        return faker.name().name();
    }

    public static String getFullUsersNameInRussian(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().name().toUpperCase(Locale.ROOT);
    }

    public static String getFullUsersNameWithDigit() {
        Faker faker = new Faker();
        return faker.number().digits(10);
    }

    public static String getFullUsersNameWithSpecCharacters() {
        return "!@#$%^&*";
    }

    public static String getOnlyUsersFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName().toUpperCase();
    }

    public static String getOnlyUsersLastName() {
        Faker faker = new Faker();
        return faker.name().lastName().toUpperCase();
    }

    public static String getCVCNumber(int count) {
        Faker faker = new Faker();
        return faker.number().digits(count);
    }

    public static String getCVCNumber() {
        Faker faker = new Faker();
        return faker.number().digits(3);
    }
}
