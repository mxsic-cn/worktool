package cn.mxsic.java8;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Function: Pojo <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-26 16:00:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pojo {
    private boolean right;
    private String good;
}
