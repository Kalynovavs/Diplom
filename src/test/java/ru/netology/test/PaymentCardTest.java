package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.StartingPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentCardTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldOpen() {
        String sutUrl = System.getProperty("sut.url");
        open(sutUrl);
    }

    @AfterEach
    void shouldClearAll() {
        SQLHelper.shouldDeleteAfterPayment();
    }

    @Test
    @DisplayName("buying with APPROVED card")
    void shouldBuySuccessfullyWithApprovedCard() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var number = DataHelper.getApprovedCardNumber();
        buyWithCard.fillTheForm(number, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCard.waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getPaymentInfo();
        assertEquals("APPROVED", paymentWithInfo.getStatus());
    }

    @Test
    @DisplayName("buying with DECLINED card")
    void shouldNotSellWithDeclinedCard() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var number = DataHelper.getDeclinedCardNumber();
        buyWithCard.fillTheForm(number, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCard.waitErrorMessage();
        var paymentWithInfo = SQLHelper.getPaymentInfo();
        assertEquals("DECLINED", paymentWithInfo.getStatus());
    }

    @Test
    @DisplayName("sending empty form")
    void shouldNotSellWhenAllFieldsAreEmpty() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        buyWithCard.emptyFields();
        buyWithCard.waitErrorMessageBecauseOfEmptyField();
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field CVC consist one digit")
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var cvc = DataHelper.getCVCNumber(1);
        buyWithCard.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field CVC consist two digit")
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var cvc = DataHelper.getCVCNumber(2);
        buyWithCard.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist only surname")
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCard.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardHolder, cvc );
        buyWithCard.waitSuccessMessage();
    }

    @Test
    @DisplayName("field employer consist only name")
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCard.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardHolder, cvc );
        buyWithCard.waitSuccessMessage();
    }

    @Test
    @DisplayName("date card is fail")
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = DataHelper.generateDate(-20,"YY");
        buyWithCard.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCard.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("date card very big")
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = DataHelper.generateDate(20000,"YY");;
        buyWithCard.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCard.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("field year consist one digit")
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = DataHelper.getYearWithOneDigit();
        buyWithCard.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field year consist '00'")
    void shouldNotSellWhenYearNumberIsZeros() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var yearNumber = DataHelper.getDateWithZero();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("number of card consist only 0")
    void shouldNotSellWhenCardNumberIsZeros() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var cardNumber = DataHelper.getCardNumberWithZero();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("field card number is not allowed")
    void shouldNotSellWhenCardNumberIsUnknown() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var cardNumber = DataHelper.getNotAlowedCardNumber();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessage();
    }

    @Test
    @DisplayName("field card number consist less 16 digits")
    void shouldNotSellWhenCardNumberIsShort() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var cardNumber = DataHelper.getSixteenDigitCardNumber();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("field month consist 00")
    void shouldNotSellWhenMonthNumberIsZeros() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = DataHelper.getDateWithZero();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("field month consist one digit")
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = DataHelper.getMonthWithOneDigit();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field month consist does not exist month")
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = DataHelper.getNotExistMonth();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("field employer consist one letter")
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardholder = DataHelper.getUsersNameByLength(1);
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist many letters")
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardholder = DataHelper.getUsersNameByLength(60);
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist low case letters")
    void shouldNotSellWhenNameOfCardholderInLowerCaseLetters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardholder = DataHelper.getFullUsersNameInLowCaseLetters();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitSuccessMessage();
    }

    @Test
    @DisplayName("field employer consist different case letters")
    void shouldNotSellWhenNameOfCardholderInUpperCaseAndLowerCaseLetters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardholder = DataHelper.getFullUsersNameInUpperCaseAndLowCaseLetters();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        buyWithCardPage.waitSuccessMessage();
    }

    @Test
    @DisplayName("field employer consist russian language letters")
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardholder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist digits")
    void shouldNotSellWhenNameOfCardholderInContainsNumbers() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardholder =DataHelper.getFullUsersNameWithDigit();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist special characters")
    void shouldNotSellWhenNameOfCardholderInContainsSpecialCharacters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc =DataHelper.getCVCNumber();
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardholder = DataHelper.getFullUsersNameWithSpecCharacters();
        buyWithCardPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }
}
