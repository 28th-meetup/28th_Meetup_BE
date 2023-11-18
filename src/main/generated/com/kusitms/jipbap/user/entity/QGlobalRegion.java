package com.kusitms.jipbap.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGlobalRegion is a Querydsl query type for GlobalRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlobalRegion extends EntityPathBase<GlobalRegion> {

    private static final long serialVersionUID = 815132524L;

    public static final QGlobalRegion globalRegion = new QGlobalRegion("globalRegion");

    public final StringPath countryKorean = createString("countryKorean");

    public final StringPath countryLongName = createString("countryLongName");

    public final StringPath countryShortName = createString("countryShortName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath regionKorean = createString("regionKorean");

    public final StringPath regionName = createString("regionName");

    public QGlobalRegion(String variable) {
        super(GlobalRegion.class, forVariable(variable));
    }

    public QGlobalRegion(Path<? extends GlobalRegion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGlobalRegion(PathMetadata metadata) {
        super(GlobalRegion.class, metadata);
    }

}

