package dev.workers.belokurov.demo.repository;

import dev.workers.belokurov.demo.entity.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {

}
