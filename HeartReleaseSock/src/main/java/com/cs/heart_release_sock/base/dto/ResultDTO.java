package com.cs.heart_release_sock.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ResultDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/18 23:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "返回响应数据")
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = -8432552859708263047L;
    @ApiModelProperty(value = "提示码",required = true)
    private Integer code;
    @ApiModelProperty(value = "提示信息")
    private String message;
    @ApiModelProperty(value = "数据")
    private T data;
}
