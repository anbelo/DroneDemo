package dev.workers.belokurov.demo.service.audit;

import dev.workers.belokurov.demo.entity.DroneAudit;
import dev.workers.belokurov.demo.repository.DroneAuditRepository;
import dev.workers.belokurov.demo.repository.DroneRepository;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@EnableAsync
public class Cron {

    private final DroneRepository droneRepository;

    private final DroneAuditRepository droneAuditRepository;

    public Cron(DroneRepository droneRepository, DroneAuditRepository droneAuditRepository) {
        this.droneRepository = droneRepository;
        this.droneAuditRepository = droneAuditRepository;
    }

    @Scheduled(initialDelay = 10_000, fixedRateString = "${service.audit.fixed-rate}")
    public void dronesBatteryAudit() {
        droneAuditRepository.saveAll(
        droneRepository.findAll().stream()
                .map(drone -> new DroneAudit(drone.getSerialNumber(), drone.getBatteryCapacity()))
                .toList()
        );
    }
}
