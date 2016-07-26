package org.lcinga.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Created by lcinga on 2016-07-26.
 */
@Entity
public class LazyImage {
    @Id
    private long id;

    @Lob
    @Column(name="LARGE_IMAGE", nullable=false)
    private byte[] largeImage;

}
