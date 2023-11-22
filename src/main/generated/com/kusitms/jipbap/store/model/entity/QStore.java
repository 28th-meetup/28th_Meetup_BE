package com.kusitms.jipbap.store.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = -415826667L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final com.kusitms.jipbap.common.entity.QDateEntity _super = new com.kusitms.jipbap.common.entity.QDateEntity(this);

    public final StringPath address = createString("address");

    public final NumberPath<Double> avgRate = createNumber("avgRate", Double.class);

    public final NumberPath<Long> bookmarkCount = createNumber("bookmarkCount", Long.class);

    public final EnumPath<com.kusitms.jipbap.user.model.entity.CountryPhoneCode> countryPhoneCode = createEnum("countryPhoneCode", com.kusitms.jipbap.user.model.entity.CountryPhoneCode.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deliveryRegion = createString("deliveryRegion");

    public final StringPath description = createString("description");

    public final StringPath detailAddress = createString("detailAddress");

    public final BooleanPath foodChangeYn = createBoolean("foodChangeYn");

    public final com.kusitms.jipbap.user.model.entity.QGlobalRegion globalRegion;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath image2 = createString("image2");

    public final StringPath image3 = createString("image3");

    public final BooleanPath koreanYn = createBoolean("koreanYn");

    public final NumberPath<Double> minOrderAmount = createNumber("minOrderAmount", Double.class);

    public final StringPath name = createString("name");

    public final StringPath operationTime = createString("operationTime");

    public final com.kusitms.jipbap.user.model.entity.QUser owner;

    public final StringPath phoneNum = createString("phoneNum");

    public final NumberPath<Long> rateCount = createNumber("rateCount", Long.class);

    public final NumberPath<Long> reviewCount = createNumber("reviewCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.globalRegion = inits.isInitialized("globalRegion") ? new com.kusitms.jipbap.user.model.entity.QGlobalRegion(forProperty("globalRegion")) : null;
        this.owner = inits.isInitialized("owner") ? new com.kusitms.jipbap.user.model.entity.QUser(forProperty("owner"), inits.get("owner")) : null;
    }

}

