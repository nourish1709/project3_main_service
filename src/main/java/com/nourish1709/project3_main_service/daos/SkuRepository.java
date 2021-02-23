package com.nourish1709.project3_main_service.daos;

import com.nourish1709.project3_main_service.models.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long>
{
}
