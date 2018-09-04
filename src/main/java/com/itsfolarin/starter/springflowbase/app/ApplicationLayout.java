package com.itsfolarin.starter.springflowbase.app;

import com.itsfolarin.starter.springflowbase.backend.service.AuthService;
import com.itsfolarin.starter.springflowbase.ui.views.nav.AppToolbar;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouteNotFoundError;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Theme(Lumo.class)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=no")
@Component
public class ApplicationLayout extends VerticalLayout implements RouterLayout, PageConfigurator, BeforeEnterObserver {

    private RootLayout root;

    public ApplicationLayout() {

    }

    protected void init() {
        add(new AppToolbar());
        add(root = new RootLayout());
    }

    @Override
    public void configurePage(InitialPageSettings initialPageSettings) {
        initialPageSettings.addMetaTag("apple-mobile-web-app-capable", "yes");
        initialPageSettings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null)
            root.setContent(content);

    }

    @Override
    public Optional<UI> getUI() {
        return Optional.ofNullable(UI.getCurrent());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!AuthService.isLoggedIn())
            beforeEnterEvent.rerouteToError(NotAuthenticatedException.class);
        else if (root == null) init();

        if (!AuthService.isAccessGranted(beforeEnterEvent.getNavigationTarget()))
            beforeEnterEvent.rerouteToError(AccessDeniedException.class);
    }

    private class RootLayout extends Composite<Div> {

        void setContent(HasElement content) {
            getElement().removeAllChildren();
            getElement().appendChild(content.getElement());
        }
    }
}
