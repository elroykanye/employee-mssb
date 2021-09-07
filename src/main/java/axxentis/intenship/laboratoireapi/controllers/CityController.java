package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.City;
import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.payload.dto.CityDto;
import axxentis.intenship.laboratoireapi.payload.dto.CountryDto;
import axxentis.intenship.laboratoireapi.payload.dto.DepartmentDto;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            List<CityDto> cityDtos = mapList(cities, CityDto.class);
            return ResponseEntity.ok(new ApiResponse(true, cityDtos, "Get cities successfully", HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, "THe list is empty", HttpStatus.NOT_FOUND));
        }
    }

    //Get city by id
    @GetMapping("{id}")
    public ResponseEntity<?> getCityByid(@PathVariable("id") final Long id){
        Optional<City> city = cityService.getCity(id);
        if (city.isPresent()){
            CityDto cityDto =  mapCityToCityDTOs(city);
            return ResponseEntity.ok(new ApiResponse(true, city, "Get city successfully", HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "THe list is empty", HttpStatus.NO_CONTENT));
        }
    }

    // Create City
    @PostMapping("/add")
    public ResponseEntity<?>createCity(@RequestBody City city){
        City saveCity = cityService.saveCity(city);
        CityDto cityDto = mapCityToCityDTO(saveCity);
        return ResponseEntity.ok(new ApiResponse(true, cityDto, "Save succesfully", HttpStatus.OK));
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

            CityDto cityDto = mapCityToCityDTO(cityService.saveCity(currentCity));
            return ResponseEntity.ok(new ApiResponse(true, cityDto, "City updated succesfully", HttpStatus.OK));

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

    // Methods for Dto implementation

    /**
     * Transforme source List to dto list targetClass
     *
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }

    /**
     * Transform single department to  DepartmentDto
     *
     *
     * @param city
     *
     * @return
     */
    private CityDto mapCityToCityDTO(City city) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(city, CityDto.class);

    }
    /**
     * Transform single department to  DepartmentDto
     *
     *
     * @param city
     *
     * @return
     */
    private CityDto mapCityToCityDTOs(Optional<City> city) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(city, CityDto.class);
    }


}
