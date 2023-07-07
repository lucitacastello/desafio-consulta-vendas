package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesSummaryDTO(obj.seller.name, SUM(obj.amount)) "  +
            "FROM Sale obj " +
            "WHERE (CAST(:min AS date) IS NULL OR obj.date >= :min) " +
            "AND (CAST(:max AS date) IS NULL OR obj.date <= :max) " +
            "GROUP BY obj.seller.name")
    Page<SalesSummaryDTO> searchSummary(LocalDate min, LocalDate max, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) "  +
            "FROM Sale obj " +
            "WHERE (CAST(:min AS date) IS NULL OR obj.date >= :min) " +
            "AND (CAST(:max AS date) IS NULL OR obj.date <= :max) " +
            "AND (:name IS NULL OR UPPER(obj.seller.name) LIKE CONCAT('%', UPPER(:name), '%'))")
    Page<SalesReportDTO> searchReport(LocalDate min, LocalDate max, String name, Pageable pageable);

}
