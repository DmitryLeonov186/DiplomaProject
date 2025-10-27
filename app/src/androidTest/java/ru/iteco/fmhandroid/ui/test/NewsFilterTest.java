package ru.iteco.fmhandroid.ui.test;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.generateRandomThreeDigitString;
import static ru.iteco.fmhandroid.ui.pageObject.NewsPage.ACTIVE;
import static ru.iteco.fmhandroid.ui.pageObject.NewsPage.NOT_ACTIVE;

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
@Epic(value = "Поиск новостей на странице управления «News»")
public class NewsFilterTest {

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
        newsPage.openNewsManagementPage();
    }

    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();

    @Test
    @DisplayName("Переход к разделу 'Main'")
    public void shouldGoToMainPageFromNewsManagementPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnMain();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Переход к разделу 'News'")
    public void shouldGoToNewsPageFromNewsManagementPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnNews();
        newsPage.showNewsManagementButton();
    }

    @Test
    @DisplayName("Переход к разделу 'About'")
    public void shouldGoToAboutPageFromNewsManagementPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonIsVisible();
    }

    @Test
    @DisplayName("Переход к разделу 'Love Is All'")
    public void shouldGoToOurMissionPageFromNewsManagementPage() {
        mainPage.openPageWithQuotes();
        loveIsAllPage.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи")
    public void shouldLogOutOfAccountOnTheNewsManagementPage() {
        authPage.clickOnProfileButton();
        authPage.clickOnLogout();
        authPage.LoginPageIsVisible();
    }

    @Test
    @DisplayName("Выбор поочередно каждой категории")
    public void enterAllCategoriesInTheFilterOneByOne() {
        newsPage.openNewsFilter();
        editingNewsPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Сортировка по кнопке из Control Panel")
    public void verifyNewsDateSorting() {
        String firstDateBeforeSorting = newsPage.getFirstNewsDate();
        String lastDateBeforeSorting = newsPage.getLastNewsDate();
        newsPage.clickOnSortingNews();
        String firstDateAfterSorting = newsPage.getFirstNewsDate();
        String lastDateAfterSorting = newsPage.getLastNewsDate();
        assertEquals(lastDateBeforeSorting, firstDateAfterSorting);
        assertEquals(firstDateBeforeSorting, lastDateAfterSorting);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по периоду времени")
    public void shouldFilterNewsByDateRange() {
        newsPage.openNewsFilter();
        newsPage.enterFromWhatDate(-9); // Дней назад
        newsPage.enterUntilWhatDate(9); // Дней вперед
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsDateRange(-9, 9);
    }

    @Test
    @DisplayName("Поиск новости по дате")
    public void shouldSearchForNewsViaFilterForCurrentDate() {
        editingNewsPage.createNews(Data.SALARY_CATEGORY, randomTitle, 30, "20:00", Data.DESCRIPTION_TEXT);
        newsPage.openNewsFilter();
        editingNewsPage.inputNewsCategory(Data.SALARY_CATEGORY);
        newsPage.enterFromWhatDate(30);
        newsPage.enterUntilWhatDate(30);
        newsPage.clickOnApplyFilterButton();
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.checkSearchResultIsDisplayed(randomTitle);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'ACTIVE'")
    public void checkAllNewsAreActive() {
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxNotActive();
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsStatus(ACTIVE);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'NOT ACTIVE'")
    public void checkAllNewsAreNotActive() {
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxActive();
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsStatus(NOT_ACTIVE);
    }

    @Test
    @DisplayName("Поиск  новости со статусом NOT ACTIVE")
    public void shouldFilterNewsByStatusNotActive() {
        editingNewsPage.statusOfEditedNewsIsNotActive(randomTitle);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxActive();
        newsPage.clickOnApplyFilterButton();
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.checkStatusOfEditedNews(randomTitle, NOT_ACTIVE);
    }

    @Test
    @DisplayName("Поиск новости со статусом ACTIVE")
    public void shouldFilterNewsByStatusActive() {
        editingNewsPage.createNews(Data.MASSAGE_CATEGORY, randomTitle, 0, "20:00", Data.DESCRIPTION_TEXT);
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxNotActive();
        newsPage.clickOnApplyFilterButton();
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.checkStatusOfEditedNews(randomTitle, ACTIVE);
    }

    @Test
    @DisplayName("Отмена применения фильтра новостей")
    public void shouldAllowToCancelNewsFilterApplication() {
        newsPage.openNewsFilter();
        editingNewsPage.pressCancel();
        newsPage.verifyTextAfterCancelingFilter(Data.CONTROL_PANEL_TEXT);
    }
}