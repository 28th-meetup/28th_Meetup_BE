package com.kusitms.jipbap.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1997030305L;

    public static final QPost post = new QPost("post");

    public final com.kusitms.jipbap.common.entity.QDateEntity _super = new com.kusitms.jipbap.common.entity.QDateEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> createdBy = createDateTime("createdBy", java.time.LocalDateTime.class);

    public final NumberPath<Long> hit = createNumber("hit", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Post.PostType> postType = createEnum("postType", Post.PostType.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final DateTimePath<java.time.LocalDateTime> updatedBy = createDateTime("updatedBy", java.time.LocalDateTime.class);

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}

