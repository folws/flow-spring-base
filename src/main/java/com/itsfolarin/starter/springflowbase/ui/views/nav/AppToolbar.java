package com.itsfolarin.starter.springflowbase.ui.views.nav;

import com.itsfolarin.starter.springflowbase.app.ApplicationLayout;
import com.itsfolarin.starter.springflowbase.backend.service.AuthService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.ParentLayout;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.LinkedList;
import java.util.List;

@ParentLayout(ApplicationLayout.class)
@Getter
public class AppToolbar extends FlexLayout {

    private final List<View> menuItems;
    private View defaultView;
    private Tabs tabs;

    public AppToolbar() {
        this.menuItems = new LinkedList<>();
        this.tabs = new Tabs();

        UserDetails user = AuthService.getCurrentUser();
        View[] views = new View[]{View.HOME};

        for (View view : views)
            if (view.isEnabled() &&
                    view.isRoleSufficient(user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .findFirst()
                            .orElse(null)))
                addMenuItem(view);

        getStyle().set("flex-direction", "column");

        add(tabs);
    }

    private void addMenuItem(View view) {
        if (view != null) {
            this.tabs.add(new Tab(new Label(view.getLabel()), view.getIcon()));

            this.menuItems.add(view);
        }
    }

    public void setDefaultView(View defaultView) {
        if (!menuItems.contains(defaultView))
            addMenuItem(defaultView);

        this.defaultView = defaultView;
    }
}
