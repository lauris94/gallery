package org.lcinga.ui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created by lcinga on 2016-07-29.
 */
public class WicketApp extends WebApplication {

    public Class<? extends WebPage> getHomePage() {
        return MyPage.class;
    }
}
