package com.itsfolarin.starter.springflowbase.backend.security;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;

public class VaadinSessionSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    private static final ThreadLocal<SecurityContext> backupContextHolder = new ThreadLocal<>();

    public VaadinSessionSecurityContextHolderStrategy() {

    }

    @Override
    public void clearContext() {
        setContext(null);
    }

    @Override
    public SecurityContext getContext() {
        VaadinSession session = getSession();

        SecurityContext context = session == null ? backupContextHolder.get()
                : (SecurityContext) session.getAttribute(SecurityContext.class.getName());

        if (context == null) {
            context = createEmptyContext();
            setContext(context);
        }

        return context;
    }

    @Override
    public void setContext(SecurityContext securityContext) {
        VaadinSession session = getSession();
        if (session != null)
            session.setAttribute(SecurityContext.class.getName(), securityContext);
        else if (securityContext != null) backupContextHolder.set(securityContext);
        else backupContextHolder.remove();
    }

    private VaadinSession getSession() {
//        if (session == null)
//            throw new IllegalStateException("No session found in current thread.");

        return VaadinSession.getCurrent();
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }
}
