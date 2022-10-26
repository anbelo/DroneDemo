package dev.workers.belokurov.demo.service.rest;

import dev.workers.belokurov.demo.entity.Drone;
import dev.workers.belokurov.demo.entity.Medication;
import dev.workers.belokurov.demo.entity.State;
import dev.workers.belokurov.demo.repository.DroneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;

/**
 * The service allow:
 * <ul>
 * <li> registering a drone;</li>
 * <li> loading a drone with medication items;</li>
 * <li> checking loaded medication items for a given drone;</li>
 * <li> checking available drones for loading;</li>
 * <li> check drone battery level for a given drone;</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1")
public class DroneController {

    private static final Integer BATTERY_CAPACITY_MIN = 25;

    private static final Integer DRONE_WEIGHT_MAX = 500;

    private final DroneRepository droneRepository;

    public DroneController(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    /**
     * Register (add) Drone
     * @param drone drone
     * @return registered (added) drone
     */
    @PostMapping("/drones")
    ResponseEntity<Drone> registerDrone(@Valid @RequestBody Drone drone) {
        return new ResponseEntity<>(droneRepository.save(drone), HttpStatus.CREATED);
    }

    /**
     * Find drone by id
     * @param id drone's id
     * @return return drone
     */
    @GetMapping("/drones/{id}")
    Drone getDrone(@PathVariable Long id) {
        return droneRepository.findById(id).orElseThrow();
    }

    /**
     * Update existed drone
     * @param drone drone
     * @return updated drone
     */
    @PutMapping("/drones")
    synchronized Drone updateDrone(@Valid @RequestBody Drone drone) {
        if (drone.getState() == State.DELIVERED) {
            drone.setWeight(0);
            drone.setMedicationList(Collections.emptyList());
        } else if (drone.getState() == State.LOADING && drone.getBatteryCapacity() < BATTERY_CAPACITY_MIN) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Too low battery level (<25%) for LOADING state");
        }
        return droneRepository.save(drone);
    }

    @DeleteMapping("/drones/{id}")
    void unregisterDrone(@PathVariable Long id) {
        droneRepository.deleteById(id);
    }

    /**
     * Add medication to drone
     * @param id drone's id
     * @param medication medication
     * @return added medication
     */
    @PostMapping("/drones/{id}/medication")
    @Transactional
    synchronized Medication addMedicationByDroneId(@PathVariable Long id, @Valid @RequestBody Medication medication) {
        var drone = droneRepository.findById(id).orElseThrow();
        var newWeight = drone.getWeight() + medication.getWeight();
        if (newWeight <= DRONE_WEIGHT_MAX) {
            medication.setDroneId(drone.getId());
            drone.setWeight(newWeight);
            var medicationList = drone.getMedicationList();
            medicationList.add(medication);
            drone = droneRepository.save(drone);
            return drone.getMedicationList().get(medicationList.size() - 1);
        }
        throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE);
    }

    /**
     * Returns the number of available drones for loading
     * @return count of available drones
     */
    @GetMapping("/drones/available")
    Long getAvailableForLoadingDrones() {
        return droneRepository.findAll().stream()
                .filter(d -> d.getState() == State.IDLE && BATTERY_CAPACITY_MIN <= d.getBatteryCapacity())
                .count();
    }

    /**
     * Returns the drone's battery level by drone id
     * @param id drone id
     * @return battery level (percentage)
     */
    @GetMapping("/drones/{id}/battery")
    Integer getBatteryLevel(@PathVariable Long id) {
        return droneRepository.findById(id).map(Drone::getBatteryCapacity).orElseThrow();
    }

}
