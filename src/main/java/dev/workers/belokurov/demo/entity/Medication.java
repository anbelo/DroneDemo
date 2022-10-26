package dev.workers.belokurov.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * Medication has:
 * <ul>
 * <li> name (allowed only letters, numbers, ‘-‘, ‘_’);</li>
 * <li> weight;</li>
 * <li> code (allowed only upper case letters, underscore and numbers);</li>
 * <li> image (picture of the medication case).</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@Entity
public class Medication {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Allowed only letters, numbers, ‘-‘, ‘_’)
     */
    @Pattern(regexp = "^(-?\\w?)+$")
    private String name;

    @NonNull
    private Integer weight;

    /**
     * Allowed only upper case letters, underscore and numbers
     */
    @Pattern(regexp = "^([A-Z0-9_])+$")
    @NonNull
    private String code;

    @Lob
    private byte[] image;

    @NonNull
    private Long droneId;

    public Medication(String name, @NonNull Integer weight, @NonNull String code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

}
