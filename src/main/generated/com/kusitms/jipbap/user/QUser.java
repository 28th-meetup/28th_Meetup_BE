package com.kusitms.jipbap.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 333743745L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.kusitms.jipbap.common.entity.QDateEntity _super = new com.kusitms.jipbap.common.entity.QDateEntity(this);

    public final StringPath address = createString("address");

    public final EnumPath<CountryPhoneCode> countryPhoneCode = createEnum("countryPhoneCode", CountryPhoneCode.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath email = createString("email");

    public final StringPath fcmToken = createString("fcmToken");

    public final com.kusitms.jipbap.user.entity.QGlobalRegion globalRegion;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath oauth = createString("oauth");

    public final StringPath password = createString("password");

    public final StringPath phoneNum = createString("phoneNum");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.globalRegion = inits.isInitialized("globalRegion") ? new com.kusitms.jipbap.user.entity.QGlobalRegion(forProperty("globalRegion")) : null;
    }

}

