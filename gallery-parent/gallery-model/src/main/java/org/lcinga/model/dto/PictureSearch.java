package org.lcinga.model.dto;

import org.lcinga.model.entities.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcinga on 2016-08-10.
 */
public class PictureSearch implements Serializable {

    private String textInput;
    private List<Tag> selectedTags = new ArrayList<>();
    private SearchByNameStatus searchByNameStatus = SearchByNameStatus.WITH_LIKE;

    public enum SearchByNameStatus {
        WITH_LIKE,
        WITHOUT_LIKE
    }

    public SearchByNameStatus getSearchByNameStatus() {
        return searchByNameStatus;
    }

    public void setSearchByNameStatus(SearchByNameStatus searchByNameStatus) {
        this.searchByNameStatus = searchByNameStatus;
    }

    public String getTextInput() {
        return textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }

    public List<Tag> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<Tag> selectedTags) {
        this.selectedTags = selectedTags;
    }
}
