package com.qapital.savings.rule;

import java.util.List;

import com.qapital.savings.event.SavingsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SavingsRepository extends JpaRepository<SavingsEvent, Long> {
    List<SavingsEvent> findAllById(Long id);

}
