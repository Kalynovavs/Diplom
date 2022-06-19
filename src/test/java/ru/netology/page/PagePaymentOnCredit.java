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
    private SelenideElement successMessage = $(".notification_status_ok");
    private SelenideElement errorMessage = $(".notification_status_error");
    private SelenideElement errorMessageAboutWrongFormat = $(byText("Неверный формат"));
    private SelenideElement errorMessageAboutWrongDateOfExpiry = $(byText("Неверно указан срок действия карты"));
    private SelenideElement errorMessageWithDateOfExpiry = $(byText("Истёк срок действия карты"));
    private SelenideElement errorMessageBecauseOfEmptyField = $(byText("Поле обязательно для заполнения"));

    public PagePaymentOnCredit() {
        header.shouldBe(Condition.visible);
    }

    public void fillTheForm(
            String number,
            String monthNumber,
            String yearNumber,
            String nameOfCardholder,
            String cvc
    ) {
        cardNumberField.setValue(number);
        monthField.setValue(monthNumber); //DataHelper.generateDate(1,"MM")
        yearField.setValue(yearNumber); //DataHelper.generateDate(1,"YY")
        cardholderField.setValue(nameOfCardholder); //DataHelper.getFullUsersName()
        cardValidationCodeField.setValue(cvc); //String.valueOf(DataHelper.getCVCNumber())
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