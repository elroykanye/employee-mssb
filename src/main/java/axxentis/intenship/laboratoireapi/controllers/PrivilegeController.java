package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.Privilege;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    PrivilegeService privilegeService;

    @GetMapping(value = "/all")
    public ResponseEntity<?>getAll(){
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        if (!CollectionUtils.isEmpty(privileges)){
            return ResponseEntity.ok(new ApiResponse(true, privileges, "Privileges loaded successfully" , HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, privileges, "Failed to load privileges, empty List", HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping(value = "{/id}")
    public ResponseEntity<?>getPrivilege(@PathVariable(value = "id") final Long id){
        Optional<Privilege> privilege = privilegeService.findPrivilegeById(id);
        if (privilege.isPresent()){
            return ResponseEntity.ok(new ApiResponse(true, privilege, "Privilege loaded successfully", HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, privilege, "Unsuccessful attempt, privilege not found", HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addPrivilege(@RequestBody Privilege privilege) {
        Privilege newPrivilege = privilegeService.addPrivilege(privilege);
        if (privilege.getDescription().isBlank() || privilege.getDescription().isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(false, privilege, "Please enter the privilege description!", HttpStatus.PARTIAL_CONTENT));
        }else {
            return ResponseEntity.ok(new ApiResponse(true, newPrivilege, "Privilege added successfully", HttpStatus.OK));
        }

    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?>updatePrivilege(@PathVariable(value = "id") final Long id, @RequestBody Privilege privilege){
        Privilege newPrivilege = privilegeService.updatePrivilege(id, privilege);
        if (privilegeService.findPrivilegeById(id).isEmpty()){
            return ResponseEntity.ok(new ApiResponse(false, privilege, "Privilege id " + id + " not found", HttpStatus.NOT_FOUND));
        }else {
            return ResponseEntity.ok(new ApiResponse(true, newPrivilege, "Privilege updated Successfully", HttpStatus.OK));
        }

    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?>deletePrivilege(@PathVariable(value = "id") final Long id){
        privilegeService.deletePrivilege(id);
        return ResponseEntity.ok(new ApiResponse(true, "Privilege deleted Successfully", HttpStatus.OK));
    }
}


