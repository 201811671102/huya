package com.cs.heart_release_sock.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName Message
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/7 14:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer messageNumber;
    private byte[] content;
    private Date messageTime;
}
