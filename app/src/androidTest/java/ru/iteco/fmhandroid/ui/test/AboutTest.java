package ru.iteco.fmhandroid.ui.test;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.pageObject.AboutPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;

@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Раздел «About»")
public class AboutTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    AboutPage aboutPage = new AboutPage();
    MainPage mainPage = new MainPage();

    @Before
    public void setUp() {
        try {
            authPage.signInButtonIsVisible();
        } catch (Exception e) {
            authPage.clickOnProfileButton();
            authPage.clickOnLogout();
        }
        authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authPage.clickOnSignIn();
        Helper.pauseExecution(3000);
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
    }



    @Test
    @DisplayName("Корректный URL Политики конфиденциальности")
    public void MustBeCorrectPrivacyPolicyUrl() {
        Intents.init();
        aboutPage.clickOnPrivacyPolicy();
        aboutPage.verifyIntent(Data.PRIVACY_POLICY_URL);
        Intents.release();
    }

    @Test
    @DisplayName("Корректный URL Условий использования")
    public void thereMustBeCorrectTermsOfUseUrl() {
        Intents.init();
        aboutPage.clickOnTermsOfUse();
        aboutPage.verifyIntent(Data.TERMS_OF_USE_URL);
        Intents.release();

    }

    @Test
    @DisplayName("Выход из раздела About кнопкой назад в приложении")
    public void checkingAllElementsInAboutPage() {
        aboutPage.clickOnBack();
        mainPage.theAllNewsItemIsDisplayed();
    }



}