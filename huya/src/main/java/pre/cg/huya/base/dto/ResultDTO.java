package pre.cg.huya.base.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO implements Serializable {
    private static final long serialVersionUID = 5724883594660154335L;

    public interface  fullResult{};

    @JsonView(fullResult.class)
    private String code;
    @JsonView(fullResult.class)
    private String msg;
    @JsonView(fullResult.class)
    private Object data;
}
