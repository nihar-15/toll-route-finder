package com.example.geo.geo.repository ;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.geo.geo.entity.TollRouteCache ;

import java.util.Optional;

public interface TollRouteCacheRepository extends JpaRepository<TollRouteCache, Long> {
    Optional<TollRouteCache> findBySourcePincodeAndDestinationPincode(String source, String destination);
}

