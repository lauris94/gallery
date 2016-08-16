package org.lcinga.ui;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcinga on 2016-08-01.
 */
public class HeaderPanel extends Panel {
    private static final long serialVersionUID = -5814406290036413043L;

    private String role;

    public HeaderPanel(String id) {
        super(id);

        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(authority -> {
            if (authority.getAuthority().toLowerCase().equals("role_admin")){
                role = getString("admin_role");
            }
            else if (authority.getAuthority().toLowerCase().equals("role_user")){
                role = getString("user_role");
            }
        });

        add(new Label("role", role));

        add(new Link<Void>("logout") {
            private static final long serialVersionUID = 4488141755292100592L;

            @Override
            public void onClick() {
                getSession().invalidateNow();
                SecurityContextHolder.clearContext();
                setResponsePage(LoginPage.class);
            }
        });
    }
}
