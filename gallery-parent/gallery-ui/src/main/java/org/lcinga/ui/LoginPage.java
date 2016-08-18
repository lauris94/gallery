package org.lcinga.ui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lcinga.model.entities.User;
import org.lcinga.service.UserService;

/**
 * Created by Laurynas Cinga on 2016-08-11.
 */
public class LoginPage extends WebPage {
    private static final long serialVersionUID = -4113297999506176721L;

    @SpringBean
    private UserService userService;

    public LoginPage() {
        User user = new User();
        StatelessForm form = new StatelessForm("form");

        form.add(new TextField<String>("username", new PropertyModel<>(user, "username")));
        form.add(new PasswordTextField("password", new PropertyModel<>(user, "password")));

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");

        feedbackPanel.setOutputMarkupId(true);

        form.add(feedbackPanel);
        add(form);
    }
}
