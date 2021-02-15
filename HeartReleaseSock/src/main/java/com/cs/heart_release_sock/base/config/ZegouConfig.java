package com.cs.heart_release_sock.base.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @ClassName ZegouConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/6 20:10
 **/
@Data
@Component
public class ZegouConfig {
    private final String appId = "";
    private final String appSign = "";
}
