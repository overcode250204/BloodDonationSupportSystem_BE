package com.example.BloodDonationSupportSystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blood_bag")
public class BloodInventory {

    @Id
    @Column(name="blood_type_id",length = 3,unique = true)
    private String bloodTypeId;

    @Column(name = "total_volume_ml")
    private Integer totalVolumeMl;


}
