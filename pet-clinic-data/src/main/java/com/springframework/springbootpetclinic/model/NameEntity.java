package com.springframework.springbootpetclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NameEntity extends BaseEntity{

    private String name;

    public NameEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

}
