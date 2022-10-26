package dev.workers.belokurov.demo.repository;

import dev.workers.belokurov.demo.entity.DroneAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneAuditRepository extends CrudRepository<DroneAudit, Long> {
}
