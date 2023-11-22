package com.kusitms.jipbap.order.repository;

import com.kusitms.jipbap.order.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findTop10ByOrder_Store_GlobalRegion_IdOrderByOrderCountDesc(Long regionId);

    List<OrderDetail> findTop4ByOrder_Store_GlobalRegion_IdOrderByOrder_CreatedAtDesc(Long regionId);
}
