package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.generateRandomThreeDigitString;
import static ru.iteco.fmhandroid.ui.data.Helper.getCurrentTime;
import static ru.iteco.fmhandroid.ui.data.Helper.randomCategory;
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
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.EditingNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Раздел «News». Создание, редактирование, удаление карточки новости")
public class EditingNewsAndCreatingNewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    EditingNewsPage editingNewsPage = new EditingNewsPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();

    @Before
    public void setUp() {
        try {
            authPage.signInButtonIsVisible();
        } catch (Exception e) {
            authPage.clickOnProfileButton();
            authPage.clickOnLogout();
        }
        authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authPage.signInButtonIsVisible();
        authPage.clickOnSignIn();
        Helper.pauseExecution(3000);
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnNews();
        newsPage.openNewsManagementPage();
    }

    @Test
    @DisplayName("Выбор каждой категории при создании карточки новости")
    public void theFieldShouldAcceptAllNewsCategories() {
        editingNewsPage.openPageCreatingNews();
        editingNewsPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Создание карточки новости с валидными данными")
    public void shouldCreateNews() {
        editingNewsPage.openPageCreatingNews();
        editingNewsPage.inputNewsCategory(randomCategory());
        editingNewsPage.inputNewsTitle(randomTitle);
        editingNewsPage.setDate(0);
        editingNewsPage.setTime(getCurrentTime());
        editingNewsPage.inputNewsDescription("Текст описания");
        editingNewsPage.clickOnSaveButton();
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.checkSearchResultIsDisplayed(randomTitle);
    }

    @Test
    @DisplayName("Создание карточки новости с незаполненными полями")
    public void shouldNotCreateNewsWithEmptyFields() {
        editingNewsPage.openPageCreatingNews();
        editingNewsPage.clickOnSaveButton();
        editingNewsPage.checkErrorMessage();
    }

    @Test
    @DisplayName("Отмена выхода из создания карточки новости с сохранением данных")
    public void shouldReturnToNewsCreation() {
        editingNewsPage.openPageCreatingNews();
        editingNewsPage.inputNewsCategory(Data.BIRTHDAY_CATEGORY);
        editingNewsPage.pressCancel();
        editingNewsPage.pressCancelAlertDialog();
        editingNewsPage.checkSearchResultIsDisplayed(Data.BIRTHDAY_CATEGORY);
    }

    @Test
    @DisplayName("Отмена публикации карточки новости")
    public void cancelNewsPublication() {
        editingNewsPage.openPageCreatingNews();
        editingNewsPage.pressCancel();
        editingNewsPage.pressOkAlertDialog();
        editingNewsPage.checkingAddNewsButton();
    }

    @Test
    @DisplayName("Удаление созданной карточки новости")
    public void deletingCreatedNews() {
        editingNewsPage.createNews(Data.MASSAGE_CATEGORY, randomTitle, 1, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.clickOnDeletingNews(randomTitle);
        editingNewsPage.pressOkAlertDialog();
        editingNewsPage.checkingTheResultOfDeletingNews(randomTitle);
    }

    @Test
    @DisplayName("Отмена удаления карточки новости")
    public void shouldCancelDeletionOfNews() {
        editingNewsPage.createNews(randomCategory(), randomTitle, 8, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.clickOnDeletingNews(randomTitle);
        editingNewsPage.pressCancelAlertDialog();
        editingNewsPage.checkingAddNewsButton();
    }

    @Test
    @DisplayName("Выбор поочередно каждой категории при редактировании карточки новости")
    public void enterEachCategoryInTurn() {
        editingNewsPage.createNews(randomCategory(), randomTitle, 3, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.openNewsEditor(randomTitle);
        editingNewsPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Отредактировать новость со статусом ACTIVE")
    public void editedNewsStatusShouldBeActive() {
        editingNewsPage.createNews(Data.SALARY_CATEGORY, randomTitle, 2, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.openNewsEditor(randomTitle);
        editingNewsPage.clickOnSaveButton();
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.checkStatusOfEditedNews(randomTitle, ACTIVE);
    }

    @Test
    @DisplayName("Отредактировать новость со статусом NOT ACTIVE")
    public void editedNewsStatusShouldBeNotActive() {
        editingNewsPage.createNews(Data.ANNOUNCEMENT_CATEGORY, randomTitle, 3, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.openNewsEditor(randomTitle);
        editingNewsPage.activityToggle();
        editingNewsPage.clickOnSaveButton();
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.checkStatusOfEditedNews(randomTitle, NOT_ACTIVE);
    }

    @Test
    @DisplayName("Отмена выхода из редактирования новости")
    public void shouldGoBackToEditingTheNews() {
        editingNewsPage.createNews(Data.TRADE_UNION_CATEGORY, randomTitle, 4, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.openNewsEditor(randomTitle);
        editingNewsPage.pressCancel();
        editingNewsPage.pressCancelAlertDialog();
        editingNewsPage.checkSearchResultIsDisplayed(Data.TRADE_UNION_CATEGORY);
    }

    @Test
    @DisplayName("Отмена редактирования новости")
    public void cancelEditingNews() {
        editingNewsPage.createNews(Data.NEED_HELP_CATEGORY, randomTitle, 0, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editingNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        editingNewsPage.openNewsEditor(randomTitle);
        editingNewsPage.pressCancel();
        editingNewsPage.pressOkAlertDialog();
        editingNewsPage.checkingAddNewsButton();
    }

}