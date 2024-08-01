package com.vanlang.webbanxe.repository;

import com.vanlang.webbanxe.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE OrderDetail od SET od.product = null WHERE od.product.id = :productId")
    void removeProductId(Long productId);
}
