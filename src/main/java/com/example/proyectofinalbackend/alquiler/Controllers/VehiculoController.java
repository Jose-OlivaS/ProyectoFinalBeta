package com.example.proyectofinalbackend.alquiler.Controllers;

import com.example.proyectofinalbackend.alquiler.dto.VehiculoDTO;
import com.example.proyectofinalbackend.alquiler.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehiculoController {
    @Autowired
    private VehiculoService vehicleService;

    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableVehicles() {
        return ResponseEntity.ok(vehicleService.getAvailableVehicles());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model) {
        return ResponseEntity.ok(vehicleService.searchVehicles(brand, model));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addVehicle(@RequestBody VehiculoDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.addVehicle(vehicleDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody VehiculoDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports/availability")
    public ResponseEntity<?> getAvailabilityReport() {
        return ResponseEntity.ok(vehicleService.getAvailabilityReport());
    }
}
