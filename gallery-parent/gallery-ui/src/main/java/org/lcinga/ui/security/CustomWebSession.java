package org.lcinga.ui.security;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Laurynas Cinga on 2016-08-12.
 */
public class CustomWebSession extends AuthenticatedWebSession {
    private static final long serialVersionUID = -5643825105934627535L;

    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    public CustomWebSession(Request request) {
        super(request);
        //Injector.get().inject(this);
    }

    @Override
    protected boolean authenticate(String username, String password) {
        boolean authenticated;
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            authenticated = auth.isAuthenticated();
        } catch (AuthenticationException e) {
            authenticated = false;
        }
        return authenticated;
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        if (isSignedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            addRolesFromAuthentication(roles, authentication);
        }
        return roles;
    }

    private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }

    public static CustomWebSession getCustomWebSession() {
        return (CustomWebSession) Session.get();
    }
}
