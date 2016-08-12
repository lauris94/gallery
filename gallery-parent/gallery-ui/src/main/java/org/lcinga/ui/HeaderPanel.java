package org.lcinga.ui;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by lcinga on 2016-08-01.
 */
public class HeaderPanel extends Panel {
    private static final long serialVersionUID = -5814406290036413043L;

    public HeaderPanel(String id) {
        super(id);

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
