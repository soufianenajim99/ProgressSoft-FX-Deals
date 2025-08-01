package com.progresssoft_fx_deals.tech.repositories;

import com.progresssoft_fx_deals.tech.models.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal, String> {
}
