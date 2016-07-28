package org.lcinga.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by lcinga on 2016-07-26.
 */
@Entity
@Table(name = "LAURIS_IMAGE_SOURCE")
public class ImageSource implements Serializable{
    private static final long serialVersionUID = 2233890300529307847L;

    @Id
    @GeneratedValue(generator = "idSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idSeq", sequenceName = "LAURIS_IMAGESOURCE_ID_SEQ", allocationSize = 1)
    private long id;

    @Lob
    @Column(name="LARGE_IMAGE", nullable=false)
    private byte[] largeImage;

    @Version
    private long version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(byte[] largeImage) {
        this.largeImage = largeImage;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
