package dev.workers.belokurov.demo.repository;

import dev.workers.belokurov.demo.entity.Drone;
import dev.workers.belokurov.demo.entity.Model;
import dev.workers.belokurov.demo.entity.State;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.validation.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DroneRepositoryTest {

    @Autowired
    private DroneRepository droneRepository;

    @Test
    @Rollback(value = false)
    @Order(1)
    public void createDrone() {
        Drone savedDrone = droneRepository.saveAndFlush(new Drone("SN-001", Model.LIGHTWEIGHT));
        assertEquals(1, savedDrone.getId());

        Drone savedDrone2 = droneRepository.saveAndFlush(new Drone("SN-002", Model.HEAVYWEIGHT));
        assertEquals(2, savedDrone2.getId());
    }

    @Test
    @Rollback(value = false)
    @Order(2)
    public void readDrone() {
        Drone drone = droneRepository.findById(1L).orElseThrow();
        assertEquals("SN-001", drone.getSerialNumber());
    }

    @Test
    @Rollback(value = false)
    @Order(3)
    public void updateDrone() {
        Drone drone = droneRepository.findById(1L).orElseThrow();
        drone.setState(State.LOADING);
        Drone updatedDrone = droneRepository.saveAndFlush(drone);
        assertEquals(State.LOADING, updatedDrone.getState());
        assertNotEquals(State.LOADED, updatedDrone.getState());
        assertNotEquals(State.DELIVERING, updatedDrone.getState());
        assertNotEquals(State.RETURNING, updatedDrone.getState());
    }

    @Test
    @Rollback(value = false)
    @Order(4)
    public void deleteDrone() {
        droneRepository.deleteById(1L);
        assertFalse(droneRepository.existsById(1L));
    }

    @Test
    @Order(5)
    public void batteryCapacityConstraint() {
        var drone1 = new Drone("SN-001", Model.MIDDLEWEIGHT);
        drone1.setBatteryCapacity(200);
        assertThrows(ConstraintViolationException.class, () -> droneRepository.saveAndFlush(drone1));

        var drone2 = new Drone("SN-001", Model.CRUISERWEIGHT);
        drone2.setBatteryCapacity(-5);
        assertThrows(ConstraintViolationException.class, () -> droneRepository.saveAndFlush(drone2));
    }

}