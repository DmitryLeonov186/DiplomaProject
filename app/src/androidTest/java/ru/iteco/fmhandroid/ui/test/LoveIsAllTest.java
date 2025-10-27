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
@Epic("Cтраница c тематическими цитатами «Love IS all»")
public class LoveIsAllTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    LoveIsAllPage loveIsAllPage = new LoveIsAllPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    AboutPage aboutPage = new AboutPage();

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
        mainPage.openPageWithQuotes();
    }

    @Test
    @DisplayName("Переход к странице 'Main'")
    public void shouldGoToMainPageFromQuotePage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnMain();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Преход к странице 'News'")
    public void shouldGoToNewsPageFromQuotePage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnNews();
        newsPage.showNewsManagementButton();
    }

    @Test
    @DisplayName("Переход к странице 'About'")
    public void shouldGoToAboutPageFromQuotePage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonIsVisible();
    }

    @Test
    @DisplayName("Выход из учетной записи")
    public void shouldLogOutOfAccountOnTheQuotePage() {
        authPage.clickOnProfileButton();
        authPage.clickOnLogout();
        authPage.LoginPageIsVisible();
    }

    @Test
    @DisplayName("Проверка отображения заголовков цитат")
    public void verifyQuoteTitle() {
        loveIsAllPage.assertDisplayOfAllQuoteTitles();
    }

    @Test
    @DisplayName("Отображение описания цитаты 1")
    public void shouldDisplayDescriptionOfQuotes1() {
        loveIsAllPage.expandQuoteByPosition(0);
        loveIsAllPage.scrollToQuotePosition(0);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_1);
    }

    @Test
    @DisplayName("Отображение описания цитаты 2")
    public void shouldDisplayDescriptionOfQuotes2() {
        loveIsAllPage.expandQuoteByPosition(1);
        loveIsAllPage.scrollToQuotePosition(1);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_2);
    }
    @Test
    @DisplayName("Отображение описания развернутой цитаты 3")
    public void shouldDisplayDescriptionOfQuotes3() {
        loveIsAllPage.expandQuoteByPosition(2);
        loveIsAllPage.scrollToQuotePosition(2);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_3);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 4")
    public void shouldDisplayDescriptionOfQuotes4() {
        loveIsAllPage.expandQuoteByPosition(3);
        loveIsAllPage.scrollToQuotePosition(3);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_4);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 5")
    public void shouldDisplayDescriptionOfQuotes5() {
        loveIsAllPage.expandQuoteByPosition(4);
        loveIsAllPage.scrollToQuotePosition(4);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_5);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 6")
    public void shouldDisplayDescriptionOfQuotes6() {
        loveIsAllPage.expandQuoteByPosition(5);
        loveIsAllPage.scrollToQuotePosition(5);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_6);
    }


    @Test
    @DisplayName("Отображение описания цитаты 7")
    public void shouldDisplayDescriptionOfQuotes7() {
        loveIsAllPage.expandQuoteByPosition(6);
        loveIsAllPage.scrollToQuotePosition(6);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_7);
    }

    @Test
    @DisplayName("Отображение описания развернутой 8")
    public void shouldDisplayDescriptionOfQuotes8() {
        loveIsAllPage.expandQuoteByPosition(7);
        loveIsAllPage.scrollToQuotePosition(7);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_8);
    }
}