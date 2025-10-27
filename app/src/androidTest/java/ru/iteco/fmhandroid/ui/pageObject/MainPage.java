package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;


public class MainPage {

    public static ViewInteraction HAMBURGER_MENU_BUTTON = onView(withId(R.id.main_menu_image_button));
    public static ViewInteraction NEWS_BUTTON = onView(withText("News"));
    public static ViewInteraction MAIN_BUTTON = onView(withText("Main"));
    public static ViewInteraction ABOUT_BUTTON = onView(withText("About"));
    public static ViewInteraction ALL_NEWS_BUTTON = onView(withId(R.id.all_news_text_view));
    public static ViewInteraction LOVE_IS_ALL_BUTTON = onView(withId(R.id.our_mission_image_button));



    public void clickOnHamburgerMenu() {
        Allure.step("Нажать кнопку 'гамбургер'");
        HAMBURGER_MENU_BUTTON.check(matches(isDisplayed())).perform(click());
    }
    public void theHamburgerMenuIsDisplayed() {
        Allure.step("Проверка наличия элемента");
        HAMBURGER_MENU_BUTTON.check(matches(isDisplayed()));
    }
    public void clickOnMain() {
        Allure.step("Нажать на кнопку 'Main' в 'гамбургер-меню'");
        MAIN_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnNews() {
        Allure.step("Нажать на кнопку 'News' в 'гамбургер-меню'");
        NEWS_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnAbout() {
        Allure.step("Нажать на кнопку 'About' в 'гамбургер-меню'");
        ABOUT_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void openPageWithQuotes() {
        Allure.step("Нажать на иконку в виде бабочки в 'App bar'");
        LOVE_IS_ALL_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void theAllNewsItemIsDisplayed() {
        Allure.step("Проверка наличия элемента");
        ALL_NEWS_BUTTON.check(matches(isDisplayed()));
    }

    public void clickOnAllNews() {
        Allure.step("Нажать на кнопку All News");
        ALL_NEWS_BUTTON.perform(click());
    }
}