package pre.cg.base.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    public interface  fullResult{};

    @JsonView(fullResult.class)
    private String code;
    @JsonView(fullResult.class)
    private String msg;
    @JsonView(fullResult.class)
    private Object data;
}
