package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.StartingPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayOnCreditTest {

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
    @DisplayName("Покупка с APPROVED картой")
    void shouldBuySuccessfullyWithApprovedCard() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var number = DataHelper.getApprovedCardNumber();
        buyWithCreditPage.fillTheForm(number, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitSuccessMessage();
        var paymentWithCreditInfo = SQLHelper.getPaymentWithCreditInfo();
        assertEquals("APPROVED", paymentWithCreditInfo.getStatus());
    }

    @Test
    @DisplayName("Покупка с DECLINED картой")
    void shouldNotSellWithDeclinedCard() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var number = DataHelper.getDeclinedCardNumber();
        buyWithCreditPage.fillTheForm(number, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessage();
        var paymentWithCreditInfo = SQLHelper.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus());
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldNotSellWhenAllFieldsAreEmpty() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        buyWithCreditPage.emptyFields();
        buyWithCreditPage.waitErrorMessageBecauseOfEmptyField();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит одну цифру")
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var cvc = "1";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит две цифры")
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var cvc = "12";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Фамилию")
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardHolder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Имя")
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardHolder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Срок карты истек")
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "20";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Срок карты слишком большой")
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "99";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле год введено одну цифру")
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "2";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле год введено 00")
    void shouldNotSellWhenYearNumberIsZeros() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "00";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Номер карты состоит из 0")
    void shouldNotSellWhenCardNumberIsZeros() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var cardNumber = "0000 0000 0000 0000";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты введено невалидные данные карты")
    void shouldNotSellWhenCardNumberIsUnknown() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var cardNumber = "4444 4444 4444 4443";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты введено меньше 16 цифр")
    void shouldNotSellWhenCardNumberIsShort() {
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var cardNumber = "4444 4444 4444 444";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц введено 00")
    void shouldNotSellWhenMonthNumberIsZeros() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var monthNumber = "00";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле месяц введено одну цифру")
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var monthNumber = "2";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц введено несуществующий месяц")
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String nameOfCardholder = DataHelper.getFullUsersName();
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var monthNumber = "13";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле владелец введено одну букву")
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardholder = "L";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец введено много букв")
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardholder = "IWJDNRYFBSYRHFYTVCPQZMSHRBD TGFJVNCMDKELWOQIAJZNDTMDLMREW";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец введено данные строчными буквами")
    void shouldNotSellWhenNameOfCardholderInLowerCaseLetters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardholder = DataHelper.getFullUsersNameInLowCaseLetters();
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец введено данные прописными и строчными буквами")
    void shouldNotSellWhenNameOfCardholderInUpperCaseAndLowerCaseLetters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardholder = DataHelper.getFullUsersNameInUpperCaseAndLowCaseLetters();
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец введено данные на русском языке")
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardholder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец введено цифры")
    void shouldNotSellWhenNameOfCardholderInContainsNumbers() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardholder = "1234567890";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец введено спецсимволы")
    void shouldNotSellWhenNameOfCardholderInContainsSpecialCharacters() {
        String cardNumber = DataHelper.getApprovedCardNumber();
        String monthNumber = DataHelper.generateDate(1,"MM");
        String yearNumber =  DataHelper.generateDate(1,"YY");
        String cvc = String.valueOf(DataHelper.getCVCNumber());
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardholder = "!@#$%^&*";
        buyWithCreditPage.fillTheForm(cardNumber, monthNumber, yearNumber, nameOfCardholder, cvc );
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }
}