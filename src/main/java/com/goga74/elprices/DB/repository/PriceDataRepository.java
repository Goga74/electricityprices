package com.goga74.elprices.DB.repository;

import com.goga74.elprices.DB.entity.PriceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PriceDataRepository extends JpaRepository<PriceData, Long> {
    Optional<PriceData> findByDate(LocalDate date);
}
