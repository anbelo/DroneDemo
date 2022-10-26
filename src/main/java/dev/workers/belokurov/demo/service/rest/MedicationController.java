package dev.workers.belokurov.demo.service.rest;

import dev.workers.belokurov.demo.entity.Medication;
import dev.workers.belokurov.demo.repository.MedicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class MedicationController {

    private final MedicationRepository medicationRepository;

    public MedicationController(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }


    /**
     * Upload an image for medication
     * @param id medication id
     * @param image image
     */
    @PostMapping("/medication/{id}/uploadImage")
    synchronized void addMedicationImage(@PathVariable Long id, @RequestPart MultipartFile image) {
        var medication = medicationRepository.findById(id).orElseThrow();
        try {
            medication.setImage(image.getBytes());
            medicationRepository.save(medication);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Find medication by id
     * @param id medication id
     * @return medication
     */
    @GetMapping("/medication/{id}")
    Medication getMedication(@PathVariable Long id) {
        return medicationRepository.findById(id).orElseThrow();
    }

}
