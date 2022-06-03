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
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var number = DataHelper.getApprovedCardNumber();
        buyWithCard.withCardNumber(number);
        buyWithCard.waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getPaymentInfo();
        assertEquals("APPROVED", paymentWithInfo.getStatus());
    }

    @Test
    @DisplayName("buying with DECLINED card")
    void shouldNotSellWithDeclinedCard() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var number = DataHelper.getDeclinedCardNumber();
        buyWithCard.withCardNumber(number);
        buyWithCard.waitErrorMessage();
        var paymentWithInfo = SQLHelper.getPaymentInfo();
        assertEquals("DECLINED", paymentWithInfo.getStatus());
    }

    @Test
    @DisplayName("sending empty sorm")
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
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var cvc = "1";
        buyWithCard.withCardValidationCode(cvc);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field CVC consist one digit")
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var cvc = "12";
        buyWithCard.withCardValidationCode(cvc);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist only surname")
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCard.withCardholder(nameOfCardHolder);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist only name")
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCard.withCardholder(nameOfCardHolder);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("date card is fail")
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = "20";
        buyWithCard.withYear(yearNumber);
        buyWithCard.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("date card very big")
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = "99";
        buyWithCard.withYear(yearNumber);
        buyWithCard.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("field year consist one digit")
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = "2";
        buyWithCard.withYear(yearNumber);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field year consist '00'")
    void shouldNotSellWhenYearNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var yearNumber = "00";
        buyWithCardPage.withYear(yearNumber);
        buyWithCardPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("number of card consist only 0")
    void shouldNotSellWhenCardNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var number = "0000 0000 0000 0000";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessage();
    }

    @Test
    @DisplayName("field card number is not allowed")
    void shouldNotSellWhenCardNumberIsUnknown() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var number = "4444 4444 4444 4443";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessage();
    }

    @Test
    @DisplayName("field card number consist less 16 digits")
    void shouldNotSellWhenCardNumberIsShort() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var number = "4444 4444 4444 444";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field month consist 00")
    void shouldNotSellWhenMonthNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = "00";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("field month consist one digit")
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = "2";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field month consist does not exist month")
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = "13";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("field employer consist one letter")
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "L";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist many letters")
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "TGFJVNCMDKELWOQIAJZNDTMDLMREW IWJDNRYFBSYRHFYTVCPQZMSHRBD ";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist low case letters")
    void shouldNotSellWhenNameOfCardholderInLowerCaseLetters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInLowCaseLetters();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist different case letters")
    void shouldNotSellWhenNameOfCardholderInUpperCaseAndLowerCaseLetters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInUpperCaseAndLowCaseLetters();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist russian language letters")
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist digits")
    void shouldNotSellWhenNameOfCardholderInContainsNumbers() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "1234567890";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("field employer consist special characters")
    void shouldNotSellWhenNameOfCardholderInContainsSpecialCharacters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "!@#$%^&*";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }
}
