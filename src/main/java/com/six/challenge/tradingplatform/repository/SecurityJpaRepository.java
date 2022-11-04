package com.six.challenge.tradingplatform.repository;

import com.six.challenge.tradingplatform.constants.Queries;
import com.six.challenge.tradingplatform.model.database.SecurityDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SecurityJpaRepository extends JpaRepository<SecurityDao, UUID> {

    @Query(
            value = Queries.FIND_SECURITY_BY_NAME,
            nativeQuery = true
    )
    Optional<SecurityDao> findByName(@Param("name") String userName);
}