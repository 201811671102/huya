package pre.cg.base.phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneBase {
    private String phone;
    private String phonecode;
    private String code;
    private String msg;
}
