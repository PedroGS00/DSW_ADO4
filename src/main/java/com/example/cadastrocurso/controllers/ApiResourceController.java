package com.example.cadastrocurso.controllers;

import com.example.cadastrocurso.models.ResourceItem;
import com.example.cadastrocurso.repositories.ResourceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@Validated
public class ApiResourceController {

  private final ResourceRepository repository;

  public ApiResourceController(ResourceRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<ResourceItem> list() {
    return repository.findAll();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResourceItem> create(@Valid @RequestBody ResourceItem item) {
    ResourceItem saved = repository.save(item);
    return ResponseEntity.ok(saved);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResourceItem> update(@PathVariable Long id, @Valid @RequestBody ResourceItem item) {
    return repository.findById(id)
        .map(existing -> {
          existing.setName(item.getName());
          existing.setDescription(item.getDescription());
          return ResponseEntity.ok(repository.save(existing));
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
