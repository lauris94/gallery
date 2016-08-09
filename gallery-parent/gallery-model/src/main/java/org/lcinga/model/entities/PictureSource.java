package org.lcinga.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by lcinga on 2016-07-26.
 */
@Entity
@Table(name = "LAURIS_PICTURE_SOURCE")
public class PictureSource implements Serializable{
    private static final long serialVersionUID = 2233890300529307847L;

    @Id
    @GeneratedValue(generator = "idSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idSeq", sequenceName = "LAURIS_IMAGESOURCE_ID_SEQ", allocationSize = 1)
    private Long id;

    @Lob
    @Column(name="LARGE_IMAGE", nullable=false)
    private byte[] largeImage;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(byte[] largeImage) {
        this.largeImage = largeImage;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
