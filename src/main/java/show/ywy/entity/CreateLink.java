package show.ywy.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yzs
 */
@Data
public class CreateLink {
    private String invitationCode;
    private String spaceCode;
    private LocalDateTime createTime = LocalDateTime.now();

}
