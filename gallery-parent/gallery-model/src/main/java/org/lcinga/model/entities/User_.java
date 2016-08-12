package org.lcinga.model.entities;

import org.lcinga.model.enums.UserRoleEnum;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by Laurynas Cinga on 2016-08-11.
 */
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, UserRoleEnum> userRole;
    public static volatile SingularAttribute<User, Long> version;
}
