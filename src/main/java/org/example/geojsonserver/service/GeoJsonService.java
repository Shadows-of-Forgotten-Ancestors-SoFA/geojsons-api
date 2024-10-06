package org.example.geojsonserver.service;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoJsonService {

    private final MongoTemplate mongoTemplate;

    public Document getGeoJsonByTimestamp(int timestamp) {
        ProjectionOperation projectOperation = Aggregation.project("properties.year_month")
                .andExpression("abs(properties.year_month - " + timestamp + ")").as("difference");

        AggregationOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.ASC, "difference"));
        AggregationOperation limitOperation = Aggregation.limit(1);

        Aggregation aggregation = Aggregation.newAggregation(
                projectOperation,
                sortOperation,
                limitOperation
        );

        var aggregated = mongoTemplate.aggregate(aggregation, "geojsons", Document.class);
        var doc = mongoTemplate.findById(aggregated.getUniqueMappedResult().getObjectId("_id"), Document.class, "geojsons");
        doc.remove("_id");
        doc.remove("properties");
        return doc;
    }

}
