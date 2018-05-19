package com.example.juanpereira.happydog_petwalkers.utils;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.AllOf.allOf;

public class InputLayoutTypeTextAction implements ViewAction {

    private String textToType;

    public InputLayoutTypeTextAction(String typedText) {
        this.textToType = typedText;
    }

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(TextInputLayout.class));
    }

    @Override
    public String getDescription() {
        return "Type text into a TextInputLayout";
    }

    @Override
    public void perform(UiController uiController, View view) {
        TextInputLayout textInputLayout = (TextInputLayout) view;
        EditText editText = textInputLayout.getEditText();

        editText.setText(textToType);
    }
}
