package com.springframework.springbootpetclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "specialty")
public class Specialty extends BaseEntity{

    @Column(name = "description")
    private String description;

}
