package org.lcinga.ui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by lcinga on 2016-07-29.
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = -6256063583666398520L;

    public HomePage() {
        add(new Label("helloMessage", "Hello WicketWorld!"));       //pirmas arg id, antras value
    }
}
