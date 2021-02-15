package pre.cg.ftp.base.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResultDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/18 23:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
}
