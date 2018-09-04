package com.itsfolarin.starter.springflowbase.backend;

import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;
import com.vaadin.flow.server.VaadinSession;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;

@Component("vaadinServlet")
@VaadinServletConfiguration(productionMode = false)
@Log
public class Servlet extends VaadinServlet {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();

        getService().setSystemMessagesProvider(messageInfo -> globalSystemMessageHandler());
        getService().addSessionInitListener(this::onServletInitialise);
    }


    private void onServletInitialise(SessionInitEvent initEvent) {
        VaadinSession session = initEvent.getSession();
//        SecurityContext securityContextOwnedByFilter = SecurityContextHolder.getContext();


        log("Reached this point in code -----------------");
    }

    @Bean
    public GlobalSystemMessageHandler globalSystemMessageHandler() {
        return new GlobalSystemMessageHandler();
    }
}
