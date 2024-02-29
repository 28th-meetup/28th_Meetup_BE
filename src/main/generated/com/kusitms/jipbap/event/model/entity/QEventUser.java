package com.kusitms.jipbap.event.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventUser is a Querydsl query type for EventUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventUser extends EntityPathBase<EventUser> {

    private static final long serialVersionUID = -808229408L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventUser eventUser = new QEventUser("eventUser");

    public final com.kusitms.jipbap.common.entity.QDateEntity _super = new com.kusitms.jipbap.common.entity.QDateEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.kusitms.jipbap.user.model.entity.QUser user;

    public QEventUser(String variable) {
        this(EventUser.class, forVariable(variable), INITS);
    }

    public QEventUser(Path<? extends EventUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventUser(PathMetadata metadata, PathInits inits) {
        this(EventUser.class, metadata, inits);
    }

    public QEventUser(Class<? extends EventUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event")) : null;
        this.user = inits.isInitialized("user") ? new com.kusitms.jipbap.user.model.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

