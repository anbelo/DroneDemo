package dev.workers.belokurov.demo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class DroneAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    private String serialNumber;

    private Integer battery;

    public DroneAudit(@NonNull String serialNumber, @NonNull Integer battery) {
        this.serialNumber = serialNumber;
        this.battery = battery;
    }
}
