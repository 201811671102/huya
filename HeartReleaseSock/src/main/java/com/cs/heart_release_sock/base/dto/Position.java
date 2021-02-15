package com.cs.heart_release_sock.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Position
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/7 17:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private Double latitude;
    private Double longitude;
}
