package org.lcinga.ui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by lcinga on 2016-07-29.
 */
public class MyPage extends WebPage {

    private static final long serialVersionUID = 8571087528033928032L;

    public MyPage() {

        add(new Label("helloMessage", "Hello WicketWorld!"));       //pirmas arg id, antras value
    }
}