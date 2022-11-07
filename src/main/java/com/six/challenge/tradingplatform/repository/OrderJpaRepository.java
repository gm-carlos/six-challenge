package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.constants.Queries;
import com.six.challenge.tradingplatform.model.database.OrderDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderDao, UUID> {

    @Query(
            value = Queries.FIND_MATCHING_SELL_ORDERS,
            nativeQuery = true
    )
    List<OrderDao> findAllMatchingSellOrders(@Param("userId") String userId,
                                             @Param("price") Double price);

    @Query(
            value = Queries.FIND_MATCHING_BUY_ORDERS,
            nativeQuery = true
    )
    List<OrderDao> findAllMatchingBuyOrders(@Param("userId") String userId,
                                            @Param("price") Double price);

}