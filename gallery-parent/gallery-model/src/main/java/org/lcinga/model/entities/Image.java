package org.lcinga.model.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lcinga on 2016-07-25.
 */
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Date date;

    @Column(name = "image_code", nullable = false)
    private String imageCode;

    @Column
    private String quality;

    @Column
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;

    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    protected Image() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return imageCode.equals(image.imageCode);

    }

    @Override
    public int hashCode() {
        return imageCode.hashCode();
    }

    @Override
    public String toString() {
        return "Image{" +
                "date=" + date +
                ", quality='" + quality + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
