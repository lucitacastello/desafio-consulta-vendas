package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	private LocalDate today;
	@Autowired
	private SaleRepository repository;

	public SaleService() {
		today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	}
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SalesSummaryDTO> salesSummary(String minDate, String maxDate, Pageable pageable){
		LocalDate min = "".equals(minDate) ? today.minusYears(1L) : LocalDate.parse(minDate) ;
		LocalDate max = "".equals(maxDate) ? today : LocalDate.parse(maxDate);
		return repository.searchSummary(min, max, pageable);
	}

	public Page<SalesReportDTO> salesReport(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate min = "".equals(minDate) ? today.minusYears(1L) : LocalDate.parse(minDate) ;
		LocalDate max = "".equals(maxDate) ? today : LocalDate.parse(maxDate);
		String name1 = "".equals(name) ? null :  name;
		return repository.searchReport(min, max, name1, pageable);
	}
}
