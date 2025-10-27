package ru.iteco.fmhandroid.ui.test;

import static org.junit.Assert.assertEquals;

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
import ru.iteco.fmhandroid.ui.pageObject.EditingNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.LoveIsAllPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Раздел 'News'")
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    AboutPage aboutPage = new AboutPage();
    LoveIsAllPage loveIsAllPage = new LoveIsAllPage();
    EditingNewsPage editingNewsPage = new EditingNewsPage();

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
        mainPage.clickOnNews();
    }


    @Test
    @DisplayName("Переход к разделу 'Main'")
    public void shouldGoToMainPageFromNewsPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnMain();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test // Этот тест не проходит (неактивна кнопка "About")
    @DisplayName("Переход к разделу 'About'")
    public void shouldGoToAboutPageFromNewsPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonIsVisible();
    }

    @Test
    @DisplayName("Переход к разделу 'Love IS All'")
    public void shouldGoToOurMissionPageFromNewsPage() {
        mainPage.openPageWithQuotes();
        loveIsAllPage.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи")
    public void shouldLogOutOfAccountOnTheNewsPage() {
        authPage.clickOnProfileButton();
        authPage.clickOnLogout();
        authPage.LoginPageIsVisible();
    }

    @Test
    @DisplayName("Ввыбор поочередно каждой категории")
    public void theFieldShouldAcceptAllNewsCategories() {
        newsPage.openNewsFilter();
        editingNewsPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Фильтрация новостей по периоду времени")
    public void shouldFilterNewsWithinDateRange() {
        newsPage.openNewsFilter();
        newsPage.enterFromWhatDate(-10); // Дней назад
        newsPage.enterUntilWhatDate(10); // Дней вперед
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsDateRange(-10, 10);
    }

    @Test
    @DisplayName("Отмена применения настроек фильтра новостей")
    public void shouldCancelNewsFilterApplication() {
        newsPage.openNewsFilter();
        editingNewsPage.pressCancel();
        newsPage.verifyTextAfterCancelingFilter(Data.NEWS_TEXT);
    }

    @Test
    @DisplayName("Сортировка по кнопке")
    public void verifyNewsDateSorting() {
        String firstDateBeforeSorting = newsPage.getFirstNewsDate();
        String lastDateBeforeSorting = newsPage.getLastNewsDate();
        newsPage.clickOnSortingNews();
        String firstDateAfterSorting = newsPage.getFirstNewsDate();
        String lastDateAfterSorting = newsPage.getLastNewsDate();
        assertEquals(lastDateBeforeSorting, firstDateAfterSorting);
        assertEquals(firstDateBeforeSorting, lastDateAfterSorting);
    }


}