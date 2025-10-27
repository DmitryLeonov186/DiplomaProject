package ru.iteco.fmhandroid.ui.test;

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
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.LoveIsAllPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Основная страница «Main»")
public class MainTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    AboutPage aboutPage = new AboutPage();
    LoveIsAllPage loveIsAllPage = new LoveIsAllPage();

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
    }

    @Test
    @DisplayName("Переход к разделу 'News' из раздела 'Main'")
    public void shouldGoToNewsPageFromMainPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnNews();
        newsPage.showNewsManagementButton();
    }

    @Test
    @DisplayName("Переход к разделу 'About' из раздела 'Main'")
    public void shouldGoToAboutPageFromMainPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonIsVisible();
    }

    @Test
    @DisplayName("Переход к разделу 'Love Is all' из раздела 'Main'")
    public void shouldGoToOurMissionFromMainPage() {
        mainPage.openPageWithQuotes();
        loveIsAllPage.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи")
    public void shouldLogOutOfAccountOnTheMainPage() {
        authPage.clickOnProfileButton();
        authPage.clickOnLogout();
        authPage.LoginPageIsVisible();
    }


    @Test
    @DisplayName("Перeход на страницу 'News' через ссылку 'ALL NEWS'")
    public void goToTheButtonAllNews() {
        mainPage.clickOnAllNews();
        newsPage.showNewsManagementButton();
    }


}

