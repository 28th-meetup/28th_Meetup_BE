package com.kusitms.jipbap.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreBookmark is a Querydsl query type for StoreBookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreBookmark extends EntityPathBase<StoreBookmark> {

    private static final long serialVersionUID = 542965085L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreBookmark storeBookmark = new QStoreBookmark("storeBookmark");

    public final com.kusitms.jipbap.common.entity.QDateEntity _super = new com.kusitms.jipbap.common.entity.QDateEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.kusitms.jipbap.user.QUser user;

    public QStoreBookmark(String variable) {
        this(StoreBookmark.class, forVariable(variable), INITS);
    }

    public QStoreBookmark(Path<? extends StoreBookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreBookmark(PathMetadata metadata, PathInits inits) {
        this(StoreBookmark.class, metadata, inits);
    }

    public QStoreBookmark(Class<? extends StoreBookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
        this.user = inits.isInitialized("user") ? new com.kusitms.jipbap.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

