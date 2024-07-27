package org.bhushan.productserviceproject.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Rating extends Product {
    private float rate ;
    private int count ;
}
