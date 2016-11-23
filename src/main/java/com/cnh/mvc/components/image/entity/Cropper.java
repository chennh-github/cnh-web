package com.cnh.mvc.components.image.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/5
 */
@Data
public class Cropper {

    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    private Double rotate;

    @NotNull
    private Double scaleX;

    @NotNull
    private Double scaleY;

    @NotNull
    private Double zoom;

    @NotEmpty
    private String imgUrl;

}
