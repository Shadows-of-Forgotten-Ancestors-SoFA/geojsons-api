package org.example.geojsonserver.controller;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.example.geojsonserver.service.GeoJsonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geojsons")
public class GeoJsonController {

    private final GeoJsonService geoJsonService;

    @GetMapping("/{timestamp}")
    public ResponseEntity<Document> getGeoJson(@PathVariable("timestamp") int timestamp) {
        return ResponseEntity.ok(geoJsonService.getGeoJsonByTimestamp(timestamp));
    }
}
