package com.kusitms.jipbap.food.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodOption is a Querydsl query type for FoodOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodOption extends EntityPathBase<FoodOption> {

    private static final long serialVersionUID = 1234218806L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFoodOption foodOption = new QFoodOption("foodOption");

    public final NumberPath<Double> canadaPrice = createNumber("canadaPrice", Double.class);

    public final NumberPath<Double> dollarPrice = createNumber("dollarPrice", Double.class);

    public final QFood food;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QFoodOption(String variable) {
        this(FoodOption.class, forVariable(variable), INITS);
    }

    public QFoodOption(Path<? extends FoodOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFoodOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFoodOption(PathMetadata metadata, PathInits inits) {
        this(FoodOption.class, metadata, inits);
    }

    public QFoodOption(Class<? extends FoodOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.food = inits.isInitialized("food") ? new QFood(forProperty("food"), inits.get("food")) : null;
    }

}

