package org.lcinga.ui;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Created by lcinga on 2016-07-29.
 */
public class WicketApp extends WebApplication {

    public Class<? extends Page> getHomePage() {
        return MainPageTemplate.class;
    }

    @Override
    public void init()
    {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }
}
