package org.lcinga.ui;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created by lcinga on 2016-07-29.
 */
public class WicketApp extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init()
    {
        super.init();
        // add your configuration here
    }
}
