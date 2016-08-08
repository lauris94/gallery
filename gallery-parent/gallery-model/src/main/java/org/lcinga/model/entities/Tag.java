package org.lcinga.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by lcinga on 2016-08-08.
 */
@Entity
@Table(name = "LAURIS_TAG")
public class Tag implements Serializable{
    private static final long serialVersionUID = 553964001223164917L;

    @Id
    private Long id;

    private String text;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
