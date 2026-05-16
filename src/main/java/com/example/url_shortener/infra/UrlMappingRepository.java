package com.example.url_shortener.infra;

import org.springframework.data.repository.CrudRepository;
import com.example.url_shortener.domain.UrlMapping;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UrlMappingRepository extends CrudRepository<UrlMapping, UUID> {
    Optional<UrlMapping> findByShortCode(String shortCode);


}

