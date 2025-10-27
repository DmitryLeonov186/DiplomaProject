package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AboutPage {
    public static ViewInteraction BACK_BUTTON_ABOUT_PAGE = onView(withId(R.id.about_back_image_button));
    public static ViewInteraction PRIVACY_POLICY_LINK = onView(withId(R.id.about_privacy_policy_value_text_view));
    public static ViewInteraction TERMS_OF_USE_LINK = onView(withId(R.id.about_terms_of_use_value_text_view));

    public void backButtonIsVisible() {
        Allure.step("Отображение кнопки 'Назад'");
        BACK_BUTTON_ABOUT_PAGE.check(matches(isDisplayed()));
    }

    public void clickOnPrivacyPolicy() {
        Allure.step("Клик по ссылке 'Политика конфиденциальности'");
        PRIVACY_POLICY_LINK.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnTermsOfUse() {
        Allure.step("Клик по ссылке 'Условия использования'");
        TERMS_OF_USE_LINK.check(matches(isDisplayed())).perform(click());
    }

    public void verifyIntent(String expectedUrl) {
        Allure.step("Проверка интента с действием 'VIEW' и корректным URL");
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(expectedUrl)
        ));

    }
    public void clickOnBack() {
        Allure.step("Нажать кнопку 'Назад'");
        BACK_BUTTON_ABOUT_PAGE.check(matches(isDisplayed())).perform(click());
    }

}
