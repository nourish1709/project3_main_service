package com.nourish1709.project3_main_service.repositories;

import com.nourish1709.project3_main_service.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface AccountRepository extends JpaRepository<Account, Long> {
}
