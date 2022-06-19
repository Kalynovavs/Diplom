package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class PagePaymentOnCredit {
    private SelenideElement header = $(byText("Кредит по данным карты"));
    private SelenideElement cardNumberField = $("[placeholder= '0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder= '08']");
    private SelenideElement yearField = $("[placeholder= '22']");
    private SelenideElement cardholderField = $$(".input").find(Condition.exactText("Владелец")).$(".input__control");
    private SelenideElement cardValidationCodeField = $("[placeholder= '999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successMessage = $(withText("Операция одобрена Банком"));
    private SelenideElement errorMessage = $(withText("Банк отказал в проведении операции"));
    private SelenideElement errorMessageAboutWrongFormat = $(byText("Неверный формат"));
    private SelenideElement errorMessageAboutWrongDateOfExpiry = $(byText("Неверно указан срок действия карты"));
    private SelenideElement errorMessageWithDateOfExpiry = $(byText("Истёк срок действия карты"));
    private SelenideElement errorMessageBecauseOfEmptyField = $(byText("Поле обязательно для заполнения"));

    public PagePaymentOnCredit() {
        header.shouldBe(Condition.visible);
    }

    public void withCardNumber(String number) {
        cardNumberField.setValue(number);
        monthField.setValue(DataHelper.generateDate(1,"MM"));
        yearField.setValue(DataHelper.generateDate(1,"YY"));
        cardholderField.setValue(DataHelper.getFullUsersName());
        cardValidationCodeField.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withMonth(String monthNumber) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.generateDate(1,"MM"));
        yearField.setValue(DataHelper.generateDate(1,"YY"));
        cardholderField.setValue(DataHelper.getFullUsersName());
        cardValidationCodeField.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withYear(String yearNumber) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.generateDate(1,"MM"));
        yearField.setValue(DataHelper.generateDate(1,"YY"));
        cardholderField.setValue(DataHelper.getFullUsersName());
        cardValidationCodeField.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withCardholder(String nameOfCardholder) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
       monthField.setValue(DataHelper.generateDate(1,"MM"));
        yearField.setValue(DataHelper.generateDate(1,"YY"));
        cardholderField.setValue(nameOfCardholder);
        cardValidationCodeField.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withCardValidationCode(String cvc) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.generateDate(1,"MM"));
        yearField.setValue(DataHelper.generateDate(1,"YY"));
        cardholderField.setValue(DataHelper.getFullUsersName());
        cardValidationCodeField.setValue(cvc);
        continueButton.click();
    }

    public void emptyFields() {
        continueButton.click();
    }

    public void waitSuccessMessage() {

        successMessage.shouldBe(Condition.visible,Duration.ofSeconds(20));
    }

    public void waitErrorMessage() {

        errorMessage.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void waitErrorMessageAboutWrongFormat() {

        errorMessageAboutWrongFormat.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void waitErrorMessageAboutWrongDateOfExpiry() {
        errorMessageAboutWrongDateOfExpiry.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void waitErrorMessageWithDateOfExpiry() {
        errorMessageWithDateOfExpiry.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void waitErrorMessageBecauseOfEmptyField() {
        errorMessageBecauseOfEmptyField.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }
}