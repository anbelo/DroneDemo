package dev.workers.belokurov.demo.repository;

import dev.workers.belokurov.demo.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    @NonNull
    @Override
    List<Drone> findAll();
}
