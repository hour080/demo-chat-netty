package org.itstack.naive.chat.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求结果类
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 22:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code; //0表示结果成功，1表示结果失败
    private String message;
    private Long count;
    private Object data;

    public Result(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    public static Result errorResult(Exception e){
        return new Result(1, e.getMessage());
    }
    public static Result successResult(Object data){
        return new Result(0, "", 1L, data);
    }

    public static Result successResult(Long count, Object data) {
        return new Result(0, "", count, data);
    }
}
