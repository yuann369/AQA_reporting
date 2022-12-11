package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $x("//input[@placeholder='Город']").setValue(validUser.getCity());
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("span[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("span[data-test-id='date'] input").setValue(firstMeetingDate);
        $x("//input[@name='name']").setValue(validUser.getName());
        $x("//input[@name='phone']").setValue(validUser.getPhone());
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $x("//*[text()='Запланировать']").click();
        $(byText("Успешно!")).shouldBe(visible);
        $(By.cssSelector(".notification__content")).shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);

        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("span[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("span[data-test-id='date'] input").setValue(secondMeetingDate);
        $x("//*[text()='Запланировать']").click();

        $(By.cssSelector("[data-test-id='replan-notification']")).shouldBe(visible);
        $(By.cssSelector("[data-test-id='replan-notification'] .button__content")).click();
        $(byText("Успешно!")).shouldBe(visible);
        $(By.cssSelector(".notification__content")).shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(visible);

    }
}
