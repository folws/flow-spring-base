package com.itsfolarin.starter.springflowbase.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@Route(value = "login")
public class LoginView extends Div {

    public LoginView() {
        add(new Label("this is the login"));
    }
}
