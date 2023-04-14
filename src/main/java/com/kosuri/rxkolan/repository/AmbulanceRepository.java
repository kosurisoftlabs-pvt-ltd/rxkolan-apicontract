package com.kosuri.rxkolan.repository;

import com.kosuri.rxkolan.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, String> , JpaSpecificationExecutor<Ambulance> {
}
