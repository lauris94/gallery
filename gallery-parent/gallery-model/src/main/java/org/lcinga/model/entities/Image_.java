package org.lcinga.model.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.lcinga.model.enums.ImageQuality;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Image.class)
public abstract class Image_ {

	public static volatile SingularAttribute<Image, ImageSource> imageSource;
	public static volatile SingularAttribute<Image, byte[]> smallImage;
	public static volatile SingularAttribute<Image, Date> uploadDate;
	public static volatile SingularAttribute<Image, String> description;
	public static volatile SingularAttribute<Image, Long> id;
	public static volatile SingularAttribute<Image, Date> editDate;
	public static volatile SingularAttribute<Image, Long> version;
	public static volatile SingularAttribute<Image, ImageQuality> quality;

}

