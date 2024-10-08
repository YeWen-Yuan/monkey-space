package show.ywy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yzs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceVo {
    private WorkSpace data;
    private String tokenValue;
    private String tokenName;
    private String id;
}
