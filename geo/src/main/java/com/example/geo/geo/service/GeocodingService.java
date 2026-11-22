package com.example.geo.geo.service;

import org.springframework.stereotype.Service;
import com.example.geo.geo.client.GoogleMapsClient;

@Service
public class GeocodingService {

    private final GoogleMapsClient mapsClient;

    public GeocodingService(GoogleMapsClient mapsClient) {
        this.mapsClient = mapsClient;
    }

    public double[] geocode(String pincode) {
        var latLng = mapsClient.geocodePincode(pincode);
        return new double[]{ latLng.lat(), latLng.lng() };
    }
}

