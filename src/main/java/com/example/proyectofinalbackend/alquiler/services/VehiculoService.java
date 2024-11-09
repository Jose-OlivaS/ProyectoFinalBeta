package com.example.proyectofinalbackend.alquiler.services;

import com.example.proyectofinalbackend.alquiler.dto.VehiculoDTO;
import com.example.proyectofinalbackend.alquiler.model.Vehiculo;
import com.example.proyectofinalbackend.alquiler.Repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehicleRepository;
    private com.example.proyectofinalbackend.alquiler.model.Vehiculo Vehiculo;
    private com.example.proyectofinalbackend.alquiler.dto.VehiculoDTO VehiculoDTO;

    public List<Vehiculo> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehiculo> getAvailableVehicles() {
        return vehicleRepository.findByAvailableTrue();
    }

    public List<Vehiculo> searchVehicles(String brand, String model) {
        if (brand != null && model != null) {
            return vehicleRepository.findByBrandAndModel(brand, model);
        } else if (brand != null) {
            return vehicleRepository.findByBrand(brand);
        }
        return vehicleRepository.findAll();
    }

    public Vehiculo addVehicle(VehiculoDTO vehicleDTO) {
        Vehiculo vehicle = new Vehiculo();
        updateVehicleFromDTO(Vehiculo, VehiculoDTO);
        return vehicleRepository.save(vehicle);
    }

    public Vehiculo updateVehicle(Long id, VehiculoDTO vehicleDTO) {
        Vehiculo vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        updateVehicleFromDTO(vehicle, vehicleDTO);
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Map<String, Object> getAvailabilityReport() {
        List<Vehiculo> allVehicles = vehicleRepository.findAll();

        return Map.of(
                "totalVehicles", allVehicles.size(),
                "availableVehicles", allVehicles.stream().filter(Vehiculo::isAvailable).count(),
                "unavailableVehicles", allVehicles.stream().filter(v -> !v.isAvailable()).count(),
                "vehiclesByBrand", getVehiclesByBrand(allVehicles)
        );
    }

    private Map<String, Long> getVehiclesByBrand(List<Vehiculo> vehicles) {
        return vehicles.stream()
                .collect(Collectors.groupingBy(Vehiculo::getBrand, Collectors.counting()));
    }

    private void updateVehicleFromDTO(Vehiculo vehicle, VehiculoDTO dto) {
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setDailyRate(dto.getDailyRate());
    }
}