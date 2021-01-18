package cn.mxsic.file.dto;

import java.util.List;

import lombok.Data;

/**
 * Function: QueryData <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-01-06 15:03:00
 */
@Data
public class QueryData {
             private String code;
             private String is_general_search;
             private String message;
             private Boolean result;
             private String timestamp;
             private List<List<Tip>> tip_list;
}
