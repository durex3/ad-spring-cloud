package com.durex.ad.advice;

import com.durex.ad.exception.AdException;
import com.durex.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gelong
 * @date 2019/11/5 0:10
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException ex) {
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerException(HttpServletRequest request, Exception ex) {
        CommonResponse<String> response = new CommonResponse<>(-1, "system error");
        response.setData("system error");
        return response;
    }
}
