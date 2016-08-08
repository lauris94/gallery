package org.lcinga.model.entities;

import org.lcinga.model.enums.ImageQuality;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lcinga on 2016-07-25.
 */
@Entity
@Table(name = "LAURIS_PICTURE")
public class Picture implements Serializable {
    private static final long serialVersionUID = 2616280108275715821L;

    @Id
    @GeneratedValue(generator = "idSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idSeq", sequenceName = "LAURIS_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "UPLOAD_DATE", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    @Column(name = "EDIT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;

    private ImageQuality quality;

    private String description;

    private String name;

    @Lob
    @Column(name = "SMALL_IMAGE")
    private byte[] smallImage;

    @Version
    private long version;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private PictureSource pictureSource;

    @ManyToMany()
    @JoinTable(name = "LAURIS_PICTURE_TAG", joinColumns = {
        @JoinColumn(name = "PICTURE_ID")}, inverseJoinColumns = {@JoinColumn(name = "TAG_ID")
    })
    private List<Tag> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Picture() {
    }

    public PictureSource getPictureSource() {
        return pictureSource;
    }

    public void setPictureSource(PictureSource pictureSource) {
        this.pictureSource = pictureSource;
    }

    public byte[] getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(byte[] smallImage) {
        this.smallImage = smallImage;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "uploadDate=" + uploadDate +
                ", quality='" + quality + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
