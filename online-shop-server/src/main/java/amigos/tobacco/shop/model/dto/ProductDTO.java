package amigos.tobacco.shop.model.dto;

import amigos.tobacco.shop.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonPropertyOrder({"objectID", "name", "price", "quantity","categories"})
public class ProductDTO {

    @JsonProperty("objectID")
    private String id;

    @NotNull(message = "name_is_required")
    @NotBlank(message = "name_is_required")
    @Size.List({
            @Size(min = 4, message = "name_too_short"),
            @Size(max = 40, message = "name_too_long")
    })
    private String name;

    @Size(max = 100, message = "description_too_long")
    private String description;

    /**
     * Product's Price.
     */
    @NotNull(message = "price_is_required")
    @DecimalMin(value = "0.01", message = "price_value_error")
    private Double price;

    private Integer quantity;


    private Set<Category> categories;
}
