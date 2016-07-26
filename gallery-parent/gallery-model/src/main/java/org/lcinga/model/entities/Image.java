package org.lcinga.model.entities;

import org.lcinga.model.enums.ImageQuality;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lcinga on 2016-07-25.
 */
@Entity
public class Image implements Serializable {

    private static final long serialVersionUID = 2616280108275715821L;

    @Id
    private long id;

    @Column(name = "UPLOAD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    @Column(name = "EDIT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;

    private ImageQuality quality;

    private String description;

    @Lob
    @Column(name="SMALL_IMAGE", nullable=false)
    private byte[] smallImage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;

    }

    public ImageQuality getQuality() {
        return quality;
    }

    public void setQuality(ImageQuality quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Image() {
    }

    @Override
    public String toString() {
        return "Image{" +
                "uploadDate=" + uploadDate +
                ", quality='" + quality + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
