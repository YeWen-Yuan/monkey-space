package show.ywy.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yzs
 */
@Data
public class Clipboard {

    private String text;
    private LocalDateTime createTime;

    public Clipboard(String text) {
        this.text = text;
        createTime = LocalDateTime.now();
    }
}
