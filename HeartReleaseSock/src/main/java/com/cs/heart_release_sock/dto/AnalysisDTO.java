package com.cs.heart_release_sock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName AnalysisDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 17:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisDTO implements Serializable {
    private static final long serialVersionUID = -5068383804194659440L;
    private double sentiment;
    private String level;
}
