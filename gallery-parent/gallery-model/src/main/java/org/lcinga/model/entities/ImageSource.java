package org.lcinga.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Created by lcinga on 2016-07-26.
 */
@Entity
@Table(name = "LAURIS_IMAGE_SOURCE")
public class ImageSource {
    @Id
    private long id;

    @Lob
    @Column(name="LARGE_IMAGE", nullable=false)
    private byte[] largeImage;

    @Version
    private long version;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
