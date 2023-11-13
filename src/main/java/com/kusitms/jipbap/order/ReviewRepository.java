package com.kusitms.jipbap.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository <Review, Long>, ReviewRepositoryExtension {
}
