package org.lcinga.ui;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Laurynas Cinga on 2016-08-12.
 */
public class CustomWebSession extends AuthenticatedWebSession {
    private static final long serialVersionUID = -5643825105934627535L;

    private HttpSession httpSession;

    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;


    public CustomWebSession(Request request) {
        super(request);
        this.httpSession = ((HttpServletRequest) request.getContainerRequest()).getSession();
        Injector.get().inject(this);
    }

    @Override
    protected boolean authenticate(String username, String password) {
//        try {
//            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            if (auth.isAuthenticated()) {
//                // the authentication object has to be stored in the SecurityContextHolder and in the HttpSession manually, so that the
//                // security context will be accessible in the next request
//                SecurityContextHolder.getContext().setAuthentication(auth);
//                httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//                        SecurityContextHolder.getContext());
//                return true;
//            } else {
//                return false;
//            }
//        } catch (AuthenticationException e) {
//            return false;
//        }
        return false;
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
}
