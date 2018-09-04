package com.itsfolarin.starter.springflowbase.ui.views;

import com.itsfolarin.starter.springflowbase.app.AppConstants;
import com.itsfolarin.starter.springflowbase.app.ApplicationLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.security.RolesAllowed;

@Route(value = AppConstants.HOME_HREF, layout = ApplicationLayout.class)
public class HomeView extends Div {

    public HomeView() {
        add(new Label("Home view"));
    }
}
