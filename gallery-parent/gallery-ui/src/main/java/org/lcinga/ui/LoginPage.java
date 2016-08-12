package org.lcinga.ui;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lcinga.model.entities.User;
import org.lcinga.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Laurynas Cinga on 2016-08-11.
 */
public class LoginPage extends WebPage{
    private static final long serialVersionUID = -4113297999506176721L;

    @SpringBean
    private UserService userService;

    @SpringBean
    private UserDetailsService userDetailsService;

    public LoginPage() {
        User user = new User();
        Form form = new Form("form"){
            @Override
            protected void onSubmit() {
                HttpServletRequest servletRequest = (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();     //neateina cia
                String originalUrl = getOriginalUrl(servletRequest.getSession());
                AuthenticatedWebSession session = AuthenticatedWebSession.get();
                if (session.signIn(user.getUsername(), user.getPassword())) {
                    if (originalUrl != null) {
                        throw new RedirectToUrlException(originalUrl);
                    } else {
                        setResponsePage(getApplication().getHomePage());
                    }
                } else {
                    System.out.println("Login failed due to invalid credentials");
                }
            }
        };
        add(form);

        form.add(new TextField<String>("username", new PropertyModel<>(user, "username")));
        form.add(new PasswordTextField("password", new PropertyModel<>(user, "password")));
    }

    private String getOriginalUrl(HttpSession session) {
        // TODO: The following session attribute seems to be null the very first time a user accesses a secured page. Find out why
        // spring security doesn't set this parameter the very first time.
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null) {
            return savedRequest.getRedirectUrl();
        } else {
            return null;
        }
    }
}
