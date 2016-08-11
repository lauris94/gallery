package org.lcinga.ui;

import org.apache.wicket.markup.html.WebPage;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by lcinga on 2016-07-29.
 */
public class TemplatePage extends WebPage {
    private static final long serialVersionUID = 8571087528033928032L;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", "Lauris");
        return "admin";
    }

    public TemplatePage() {
        add(new HeaderPanel("headerPanel"));
        add(new FooterPanel("footerPanel"));
        add(new ContentPanel("contentPanel"));
        //add(new MenuPanel("menuPanel"));


    }
}
