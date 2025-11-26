package com.example.cadastrocurso.repositories;

import com.example.cadastrocurso.models.ResourceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceItem, Long> {
}
