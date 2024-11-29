package com.pizzaro.controller;

import com.pizzaro.model.Location;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class LocationController {

    @GetMapping("/api/locations")
    public List<Location> getLocations() {
        return Arrays.asList(
                new Location(53.88949648046219,27.545690248945167, "Наша вторая точка", "ул. Толстого 1"),
                new Location(53.87205600986163,27.573073264654568, "Наша третья точка", "ул. Денисовская 8")
        );
    }
}
