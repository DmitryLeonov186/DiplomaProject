package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.Helper.getCurrentTime;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.List;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.data.Helper;


public class EditingNewsPage {
    private static final Matcher<View> DELETE_NEWS_BUTTON = allOf(withId(R.id.delete_news_item_image_view), withContentDescription("News delete button"));
    private static final Matcher<View> EDIT_NEWS_BUTTON = allOf(withId(R.id.edit_news_item_image_view), withContentDescription("News editing button"));
    public static ViewInteraction ADD_NEWS_BUTTON = onView(withId(R.id.add_news_image_view));
    public static ViewInteraction CATEGORY_PUBLISH = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public static ViewInteraction TITLE_PUBLISH = onView(withId(R.id.news_item_title_text_input_edit_text));
    public static ViewInteraction DATE_PUBLISH = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    public static ViewInteraction TIME_PUBLISH = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    public static ViewInteraction DESCRIPTION_PUBLISH = onView(withId(R.id.news_item_description_text_input_edit_text));
    public static ViewInteraction SAVE_BUTTON = onView(withId(R.id.save_button));
    public static ViewInteraction CANCEL_BUTTON = onView(withId(R.id.cancel_button));
    public static ViewInteraction CANCEL_ALERT_DIALOG = onView(withId(android.R.id.button2));
    public static ViewInteraction OK_ALERT_DIALOG = onView(allOf(withId(android.R.id.button1), withText("OK")));
    public static ViewInteraction NEWS_LIST = onView(withId(R.id.news_list_recycler_view));
    public static ViewInteraction SLIDER_ACTIVE = onView(withId(R.id.switcher));


    public View decorView;

    // Создание карточки новости
    public void openPageCreatingNews() {
        Allure.step("Отркрыть страницу создания карточки новости");
        ADD_NEWS_BUTTON.check(matches(isDisplayed())).perform(click());
    }


    public void inputNewsCategory(String text) {
        Allure.step("Ввод категории новости " + text);
        CATEGORY_PUBLISH.check(matches(isDisplayed())).perform(replaceText(text));
    }

    public void inputNewsTitle(String text) {
        Allure.step("Ввод заголовка новости '" + text + "'");
        TITLE_PUBLISH.perform(replaceText(text), closeSoftKeyboard());
    }

    public void setDate(int date) {
        Allure.step("Ввод даты новости '" + date + "'");
        DATE_PUBLISH.check(matches(isDisplayed())).perform(replaceText(Helper.getDate(date)), closeSoftKeyboard());
    }

    public void setTime(String text) {
        Allure.step("Ввод времени публикации новости '" + text + "'");
        TIME_PUBLISH.check(matches(isDisplayed())).perform(replaceText(text), closeSoftKeyboard());
    }

    public void inputNewsDescription(String text) {
        Allure.step("Ввод описания новости '" + text + "'");
        DESCRIPTION_PUBLISH.check(matches(isDisplayed())).perform(replaceText(text));
    }

    public void clickOnSaveButton() {
        Allure.step("Сохранение карточки новости");
        SAVE_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void scrollingThroughTheNewsFeed(String text) {
        Allure.step("Прокрутить до опубликованной новости '" + text + "'");
        NEWS_LIST.check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(text))));
    }

    public void checkSearchResultIsDisplayed(String text) {
        Allure.step("Проверить элемент '" + text + "' на видимость");
        ViewInteraction titleView = onView(allOf(withText(text), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        titleView.check(matches(isDisplayed()));
        titleView.check(matches(withText(endsWith(text))));
    }

    public void verifySelectedCategories() {
        Allure.step("Ввод поочередно каждой категории");
        List<String> categories = Arrays.asList(
                Data.ANNOUNCEMENT_CATEGORY,
                Data.BIRTHDAY_CATEGORY,
                Data.SALARY_CATEGORY,
                Data.TRADE_UNION_CATEGORY,
                Data.HOLIDAY_CATEGORY,
                Data.MASSAGE_CATEGORY,
                Data.GRATITUDE_CATEGORY,
                Data.NEED_HELP_CATEGORY
        );
        for (String category : categories) {
            Allure.step("Установка категории '" + category + "'");
            CATEGORY_PUBLISH.check(matches(isDisplayed())).perform(replaceText(category), closeSoftKeyboard());
            onView(withText(category)).check(matches(isDisplayed()));
        }
    }

    public void checkingAddNewsButton() {
        Allure.step("Проверить видимость кнопки добавления новости");
        ADD_NEWS_BUTTON.check(matches(isDisplayed()));
    }

    public void createNews(String category, String title, int date, String time, String description) {
        Allure.step("Создать новость c заголовком '" + title + "'");
        openPageCreatingNews();
        inputNewsCategory(category);
        inputNewsTitle(title);
        setDate(date);
        setTime(time);
        inputNewsDescription(description);
        clickOnSaveButton();
    }

    // Удаление публикации
    public void clickOnDeletingNews(String text) {
        Allure.step("Удаление новости с заголовком '" + text + "'");
        onView(allOf(DELETE_NEWS_BUTTON, hasSibling(withText(text)))).perform(click());
    }

    public void checkingTheResultOfDeletingNews(String text) {
        Allure.step("Проверить удаление новости c заголовком '" + text + "'");
        onView(allOf(withText(text), isDisplayed())).check(doesNotExist());
    }

    // Нажать Cancel, нажать ok, сообщение об ошибке
    public void pressCancel() {
        Allure.step("Нажать 'Cancel'");
        CANCEL_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void pressOkAlertDialog() {
        Allure.step("Нажать OK в диалоговом окне");
        OK_ALERT_DIALOG.check(matches(isDisplayed())).perform(click());
    }

    public void pressCancelAlertDialog() {
        Allure.step("Нажать 'Cancel' в диалоговом окне");
        CANCEL_ALERT_DIALOG.check(matches(isDisplayed())).perform(click());
    }

    public void checkErrorMessage() {
        Allure.step("Cообщение об ошибке при создании карточки новости с незаполненными полями");
        onView(withText(Data.POP_UP_ERROR_MESSAGE))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    // Редактирование новости
    public void openNewsEditor(String text) {
        Allure.step("Редактирование новости с заголовком '" + text + "'");
        onView(allOf(EDIT_NEWS_BUTTON, hasSibling(withText(text)))).perform(click());
    }

    public void activityToggle() {
        Allure.step("Кнопка - слайдер изменения статуса новости");
        SLIDER_ACTIVE.check(matches(isDisplayed())).perform(click());
    }

    public ViewInteraction getNewsStatus(String title, String status) {
        Allure.step("Получение статуса новости: \"" + title + "\" с ожидаемым статусом: \"" + status + "\"");
        return onView(allOf(withText(status), withParent(withChild(withText(title)))));
    }

    public void checkStatusOfEditedNews(String title, String expectedStatus) {
        Allure.step("Проверить, что новость \"" + title + "\" имеет статус: \"" + expectedStatus + "\"");
        getNewsStatus(title, expectedStatus).check(matches(isDisplayed()));
    }

    public void statusOfEditedNewsIsNotActive(String randomTitle) {
        Allure.step("Создание и редактирование картчки новости со статусом 'NOT ACTIVE'");
        createNews(Data.ANNOUNCEMENT_CATEGORY, randomTitle, 33, getCurrentTime(), Data.DESCRIPTION_TEXT);
        scrollingThroughTheNewsFeed(randomTitle);
        openNewsEditor(randomTitle);
        activityToggle();
        clickOnSaveButton();
    }
}