package com.tenpo.challenge.repository;

import com.tenpo.challenge.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiCallLogRepository extends JpaRepository<Log, Long> {
}
