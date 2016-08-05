package org.lcinga.ui;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.navigation.paging.IPageable;

/**
 * Created by lcinga on 2016-08-05.
 */
public class CustomPagingNavigator extends AjaxPagingNavigator {

    public CustomPagingNavigator(String id, IPageable pageable) {
        super(id, pageable);
    }
}
