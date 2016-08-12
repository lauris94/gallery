package org.lcinga.ui;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.lang.PackageName;


/**
 * Created by lcinga on 2016-07-29.
 */
public class TemplatePage extends WebPage {
    private static final long serialVersionUID = 8571087528033928032L;

    public TemplatePage() {
        add(new HeaderPanel("headerPanel"));
        add(new FooterPanel("footerPanel"));
        add(new ContentPanel("contentPanel"));
        //add(new MenuPanel("menuPanel"));
    }
}
