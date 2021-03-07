package com.msp.product.dto;

import com.msp.product.entities.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Getter
@Setter
public class ProductDTO implements Serializable {

    private String id;

    @NotEmpty(message = "Name is a mandatory field")
    private String name;

    @NotEmpty(message = "Description is a mandatory field")
    private String description;

    @NotNull(message = "Price is a mandatory field")
    @Positive(message = "Price must be a positive value")
    private Double price;

    public ProductDTO() {
    }

    public ProductDTO(Product obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.description = obj.getDescription();
        this.price = obj.getPrice();
    }

}
