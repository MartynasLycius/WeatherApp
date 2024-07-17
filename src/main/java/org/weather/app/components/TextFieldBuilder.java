package org.weather.app.components;

import com.vaadin.flow.component.textfield.TextField;

public class TextFieldBuilder {
    private final TextField textField;

    public TextFieldBuilder() {
        textField = new TextField();
    }

    public TextFieldBuilder withPlaceholder(String placeholder) {
        textField.setPlaceholder(placeholder);
        return this;
    }

    public TextFieldBuilder withWidth(String width) {
        textField.setWidth(width);
        return this;
    }

    public TextFieldBuilder withRequired(boolean required) {
        textField.setRequired(required);
        return this;
    }

    public TextField build() {
        return textField;
    }
}

