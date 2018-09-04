package com.itsfolarin.starter.springflowbase.ui.views;

import com.itsfolarin.starter.springflowbase.app.NotAuthenticatedException;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;

import javax.servlet.http.HttpServletResponse;

public class NotAuthenticatedError extends Div implements HasErrorParameter<NotAuthenticatedException> {

    public NotAuthenticatedError() {
        add(new Label("Error mate. sorry"));
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent,
                                 ErrorParameter<NotAuthenticatedException> errorParameter) {

        return HttpServletResponse.SC_UNAUTHORIZED;
    }
}
