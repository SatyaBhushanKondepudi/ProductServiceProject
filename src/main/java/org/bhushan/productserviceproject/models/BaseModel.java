package org.bhushan.productserviceproject.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Base {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted = false;
}
