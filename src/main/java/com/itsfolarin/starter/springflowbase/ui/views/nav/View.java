package com.itsfolarin.starter.springflowbase.ui.views.nav;

import com.itsfolarin.starter.springflowbase.app.AppConstants;
import com.itsfolarin.starter.springflowbase.ui.views.HomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;

import java.util.stream.Stream;

public enum View {

    HOME(HomeView.class, AppConstants.HOME_HREF, new Icon(VaadinIcon.HOME), true);

    //    private final Class<?> viewClass;
    private final String href;
    private final boolean enabled;
    private final String[] rolesAllowed;
    private String label;
    private Component icon;

    View(Class<? extends Component> viewClass, String href, Icon icon, boolean enabled) {
        this(viewClass, href, getNameFromHref(href), icon, enabled);
    }

    View(Class<? extends Component> viewClass, String href, String label, Icon icon, boolean enabled) {
        this.href = href;
        this.label = label;
        this.icon = icon;
        this.enabled = enabled;
        this.rolesAllowed = findAllowedRoles(viewClass);
    }

    private static String getNameFromHref(String href) {
        return StringUtils.capitalize(href.toLowerCase().replaceAll("-", " "));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isRoleSufficient(String role) {
        return role == null ? rolesAllowed.length == 0 : Stream.of(rolesAllowed).anyMatch(role::equals);
    }

    private static String[] findAllowedRoles(Class<?> aClass) {
        Secured annotation = AnnotationUtils.findAnnotation(aClass, Secured.class);

        return annotation != null ? annotation.value() : null;
    }

    public String getLabel() {
        return label;
    }

    public Component getIcon() {
        return icon;
    }
}
