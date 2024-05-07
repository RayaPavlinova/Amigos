package amigos.tobacco.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessWithIdDTO {

    private int status;

    private String message;

    private Long id;
}
