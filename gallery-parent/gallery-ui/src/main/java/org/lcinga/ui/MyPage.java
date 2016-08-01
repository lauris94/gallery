package org.lcinga.ui;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lcinga.dao.PictureDao;
import org.lcinga.model.entities.Picture;
import org.lcinga.service.IPictureService;

/**
 * Created by lcinga on 2016-07-29.
 */
public class MyPage extends WebPage {

    private static final long serialVersionUID = 8571087528033928032L;

    private Component headerPanel;
    private Component footerPanel;
    private Component menuPanel;

    @SpringBean
    private PictureDao pictureDao;

    public MyPage() {
        add(headerPanel = new HeaderPanel("headerPanel"));
        add(footerPanel = new FooterPanel("footerPanel"));
        add(menuPanel = new MenuPanel("menuPanel"));
        Picture pictures = pictureDao.find(101L);
        //add(new Label("helloMessage", "Hello WicketWorld!"));       //pirmas arg id, antras value
    }
}
