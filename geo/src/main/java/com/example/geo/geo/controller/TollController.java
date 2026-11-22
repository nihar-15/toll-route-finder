package com.example.geo.geo.controller ;

import com.example.geo.geo.dto.* ;
import com.example.geo.geo.service.* ;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/toll-plazas")
public class TollController {

    private final GeocodingService geocodingService;
    private final RouteService routeService;
    private final TollPlazaService tollPlazaService;

    public TollController(GeocodingService geocodingService,
                               RouteService routeService,
                               TollPlazaService tollPlazaService) {
        this.geocodingService = geocodingService;
        this.routeService = routeService;
        this.tollPlazaService = tollPlazaService;
    }

    @PostMapping
    public ResponseEntity<?> getTollPlazas(@Valid @RequestBody TollPlazaRequest request) {
        String src = request.getSourcePincode();
        String dst = request.getDestinationPincode();

        if (src.equals(dst)) {
            return ResponseEntity.badRequest().body(new ErrorDto("Source and destination pincodes cannot be the same"));
        }

        // Geocode
        double[] srcLatLng = geocodingService.geocode(src);
        double[] dstLatLng = geocodingService.geocode(dst);

        // Route distance (km)
        double distanceKm = routeService.distanceKm(src, dst);

        RouteDto route = new RouteDto(src, dst, distanceKm);

        // Compute (with DB cache)
        TollPlazaResponse response = tollPlazaService.findTollsOnRouteCached(route, srcLatLng, dstLatLng);

        return ResponseEntity.ok(response);
    }

    // Small inline DTO for errors to match assignment examples
    private record ErrorDto(String error) {}
}
