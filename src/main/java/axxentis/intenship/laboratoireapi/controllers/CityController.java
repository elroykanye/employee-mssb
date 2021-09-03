package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.City;
import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    CityService cityService;

    //Get all Cities
    @GetMapping("/all")
    public ResponseEntity<?> getAllCities(){
        List<City> cities = cityService.getCities();
        if (!CollectionUtils.isEmpty(cities)){
            return ResponseEntity.ok(new ApiResponse(true, cities, "Get cities successfully", HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, "THe list is empty", HttpStatus.NOT_FOUND));
        }
    }

    //Get city by id
    @GetMapping("{id}")
    public ResponseEntity<?> getCityByid(@PathVariable("id") final Long id){
        Optional<City> city = cityService.getCity(id);
        if (city.isPresent()){
            return ResponseEntity.ok(new ApiResponse(true, city, "Get city successfully", HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "THe list is empty", HttpStatus.NO_CONTENT));
        }
    }

    // Create City
    @PostMapping("/add")
    public ResponseEntity<?>createCity(@RequestBody City city){
        City saveCity = cityService.saveCity(city);
        return ResponseEntity.ok(new ApiResponse(true, saveCity, "Save succesfully", HttpStatus.OK));
    }

    // Update city
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCity(@PathVariable("id") final Long id, @RequestBody City city){
        Optional<City> c =cityService.getCity(id);
        if (c.isPresent()){
            City currentCity = c.get();

            String name = city.getName();

            if (!name.isEmpty() || !name.isBlank()){
                currentCity.setName(name);
            }

            return ResponseEntity.ok(new ApiResponse(true, cityService.saveCity(currentCity), "City updated succesfully", HttpStatus.OK));

        }else {
            return ResponseEntity.ok(new ApiResponse(false, "City not found", HttpStatus.NO_CONTENT));
        }

    }

    // Delete city by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") final Long id){
        cityService.deleteCity(id);
        return ResponseEntity.ok(new ApiResponse(true, "City deleted succesfully", HttpStatus.OK));
    }

}
