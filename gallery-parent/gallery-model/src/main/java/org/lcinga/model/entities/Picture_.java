package org.lcinga.model.entities;

import org.lcinga.model.enums.ImageQuality;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Picture.class)
public abstract class Picture_ {

	public static volatile SingularAttribute<Picture, PictureSource> pictureSource;
	public static volatile SingularAttribute<Picture, byte[]> smallImage;
	public static volatile SingularAttribute<Picture, Date> uploadDate;
	public static volatile SingularAttribute<Picture, String> description;
	public static volatile SingularAttribute<Picture, Long> id;
	public static volatile SingularAttribute<Picture, Date> editDate;
	public static volatile SingularAttribute<Picture, Long> version;
	public static volatile SingularAttribute<Picture, ImageQuality> quality;
	public static volatile SingularAttribute<Picture, String> name;

}

