package com.durex.ad.advice;

import com.durex.ad.exception.AdException;
import com.durex.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author gelong
 * @date 2019/11/5 0:10
 */
@Slf4j
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
        log.warn("system error -> {}", Arrays.toString(ex.getStackTrace()));
        CommonResponse<String> response = new CommonResponse<>(-1, "system error");
        response.setData("system error");
        return response;
    }
}
