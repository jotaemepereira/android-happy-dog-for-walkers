package com.example.juanpereira.happydog_petwalkers;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.juanpereira.happydog_petwalkers.activities.MainActivity;
import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.ui.login.LoginActivity;
import com.example.juanpereira.happydog_petwalkers.utils.InputLayoutTypeTextAction;
import com.example.juanpereira.happydog_petwalkers.utils.TestComponentRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final IntentsTestRule<LoginActivity> main =
            new IntentsTestRule<>(LoginActivity.class, false, false);

    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void emptyFieldsShowsErrorMessage() {
        main.launchActivity(new Intent(component.getContext(), LoginActivity.class));

        onView(withId(R.id.loginButton)).perform(click());

        checkSnackbarWithText("Fields cannot be empty");
    }

    @Test
    public void invalidEmailShowsErrorMessage() {
        main.launchActivity(new Intent(component.getContext(), LoginActivity.class));

        onView(withId(R.id.emailInput)).perform(new InputLayoutTypeTextAction("wrongEmail"));
        onView(withId(R.id.passwordInput)).perform(new InputLayoutTypeTextAction("password"));
        onView(withId(R.id.loginButton)).perform(click());

        checkSnackbarWithText("Invalid email");
    }

    @Test
    public void correctLoginShowsMainActivity() {
        main.launchActivity(new Intent(component.getContext(), LoginActivity.class));

        stubDataInDataManager(Observable.just(Response.success(Void.class)));

        onView(withId(R.id.emailInput)).perform(new InputLayoutTypeTextAction("juan@hotmail.com"));
        onView(withId(R.id.passwordInput)).perform(new InputLayoutTypeTextAction("1234"));
        onView(withId(R.id.loginButton)).perform(click());

        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void invalidLoginShowsErrorMessage() {
        main.launchActivity(new Intent(component.getContext(), LoginActivity.class));

        stubDataInDataManager(
                Observable.just(
                        Response.error(
                                404,
                                ResponseBody.create(MediaType.parse("application/json"), "")
                        )
                )
        );

        onView(withId(R.id.emailInput)).perform(new InputLayoutTypeTextAction("juan@hotmail.com"));
        onView(withId(R.id.passwordInput)).perform(new InputLayoutTypeTextAction("1234"));
        onView(withId(R.id.loginButton)).perform(click());

        checkSnackbarWithText("Invalid credentials");
    }

    private void stubDataInDataManager(Observable observable) {
        when(component.getMockDataManager().login(any(LoginBody.class))).thenReturn(observable);
    }

    private void checkSnackbarWithText(String message) {
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(message)))
                .check(matches(isDisplayed()));
    }
}
