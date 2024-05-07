package com.kusitms.jipbap.event.aop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventLog is a Querydsl query type for EventLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventLog extends EntityPathBase<EventLog> {

    private static final long serialVersionUID = 1535997623L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventLog eventLog = new QEventLog("eventLog");

    public final com.kusitms.jipbap.common.entity.QDateEntity _super = new com.kusitms.jipbap.common.entity.QDateEntity(this);

    public final EnumPath<Action> action = createEnum("action", Action.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.kusitms.jipbap.event.model.entity.QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.kusitms.jipbap.user.model.entity.QUser user;

    public QEventLog(String variable) {
        this(EventLog.class, forVariable(variable), INITS);
    }

    public QEventLog(Path<? extends EventLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventLog(PathMetadata metadata, PathInits inits) {
        this(EventLog.class, metadata, inits);
    }

    public QEventLog(Class<? extends EventLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new com.kusitms.jipbap.event.model.entity.QEvent(forProperty("event"), inits.get("event")) : null;
        this.user = inits.isInitialized("user") ? new com.kusitms.jipbap.user.model.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

