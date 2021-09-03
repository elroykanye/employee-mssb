package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/country")
public class CoutryCOntroller {

    @Autowired
    CountryService countryService;

    //Get all countries
    @GetMapping("all")
    public ResponseEntity<?> getAllCountry(){
        List<Country> countries = countryService.getCountries();
        if (!CollectionUtils.isEmpty(countries)){
            return ResponseEntity.ok(new ApiResponse(true, countries, "Get country succsesfully", HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "The list is empty", HttpStatus.NO_CONTENT));
        }
    }

    //Get country by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getContryByid(@PathVariable("id") final Long id){
        Optional<Country> country = countryService.getCountry(id);
        if (country.isPresent()){
            return ResponseEntity.ok(new ApiResponse(true, country, "Get country succesfully", HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse(true, "The list is empty", HttpStatus.NO_CONTENT));
        }
    }

    // Create department
    @PostMapping("/add")
    public ResponseEntity<?>createCountry(@RequestBody Country country){
        Country saveCountry = countryService.saveCountry(country);
        return ResponseEntity.ok(new ApiResponse(true, saveCountry, "Save succesfully", HttpStatus.OK));
    }

    // Update Country
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable("id") final Long id, @RequestBody Country country){
        Optional<Country> c =countryService.getCountry(id);
        if (c.isPresent()){
            Country currentCountry = c.get();

            String name = country.getName();

            if (!name.isEmpty() || !name.isBlank()){
                currentCountry.setName(name);
            }

            String iso_code  = country.getIso_code();
            if (!iso_code.isEmpty() || !iso_code.isBlank()){
                currentCountry.setIso_code(iso_code);
            }
            String indicative  = country.getIndicative();
            if (!indicative.isEmpty() || !indicative.isBlank()){
                currentCountry.setIndicative(indicative);
            }

            return ResponseEntity.ok(new ApiResponse(true, countryService.saveCountry(currentCountry), "Country updated succesfully", HttpStatus.OK));

        }else {
            return ResponseEntity.ok(new ApiResponse(false, "Country not found", HttpStatus.NO_CONTENT));
        }
    }

    // Delete Country by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") final Long id){
        countryService.deleteCoutry(id);
        return ResponseEntity.ok(new ApiResponse(true, "Country deleted succesfully", HttpStatus.OK));
    }

}
