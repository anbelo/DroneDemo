package dev.workers.belokurov.demo.entity;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * A Drone has:
 * <ul>
 * <li> serial number (100 characters max);</li>
 * <li> model (Lightweight, Middleweight, Cruiserweight, Heavyweight);</li>
 * <li> weight limit (500gr max);</li>
 * <li> battery capacity (percentage);</li>
 * <li> state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Drone {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(length = 100, unique = true)
    private String serialNumber;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Model model;

    @Min(0) @Max(500)
    @Column(columnDefinition = "int check (weight >= 0 and weight <= 500)")
    private Integer weight = 0;

    @Min(0) @Max(100)
    @Column(columnDefinition = "int check (battery_capacity >= 0 and battery_capacity <= 100)")
    private Integer batteryCapacity = 100;

    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private List<Medication> medicationList;

    public Drone(@NonNull String serialNumber, @NonNull Model model) {
        this.serialNumber = serialNumber;
        this.model = model;
    }

}
