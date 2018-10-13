package dlg.lk.bms.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dlg.lk.bms.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sandaruwan_05083 on 9/14/2017.
 */
//29/9/17   handleBaseException() added custome exception validation - 409 /404
//4/10/17   All Enumaration Realated Runtime Exceptions Handeled 
@ControllerAdvice
@RestController
public class GloableExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseBuilder handleBaseException(RuntimeException e) {
        ResponseBuilder<String> response = new ResponseBuilder<String>();
        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            response.buildResponse(customException.getHttpStatus(), customException.getErrorMessage());

        } else {
            response.buildResponse(HttpStatus.FORBIDDEN, e.getMessage());
        }

  
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        return e.getMessage();
    }

    class ResponseBuilder<T> {

        public String code;

        @JsonIgnore
        private HttpStatus status;

        public T responseBody;

        void buildResponse(HttpStatus status, T responseBody) {
            this.status = status;
            this.code = String.valueOf(status.value());

            this.responseBody = responseBody;
        }

        ResponseEntity<?> build() {
            return ResponseEntity.status(status).body(this);
        }
    }

}
