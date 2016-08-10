package org.lcinga.model.dto;

import org.lcinga.model.entities.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcinga on 2016-08-10.
 */
public class PictureSearch implements Serializable{

    private String textInput;
    private String searchType = "With like";
    private List<Tag> selectedTags = new ArrayList<>();

    public String getTextInput() {
        return textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }


    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }


    public List<Tag> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<Tag> selectedTags) {
        this.selectedTags = selectedTags;
    }
}
