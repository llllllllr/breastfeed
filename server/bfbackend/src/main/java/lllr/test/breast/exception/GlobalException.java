package lllr.test.breast.exception;

import lllr.test.breast.common.ServerResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;



@ControllerAdvice
@ResponseBody
public class GlobalException {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServerResponse<Object> handleValidationException(ConstraintViolationException e){
        for(ConstraintViolation<?> s:e.getConstraintViolations()){
            return ServerResponse.createByErrorMsg(s.getMessage());
        }
        return ServerResponse.createByErrorMsg("请求参数不合法");
    }
}
