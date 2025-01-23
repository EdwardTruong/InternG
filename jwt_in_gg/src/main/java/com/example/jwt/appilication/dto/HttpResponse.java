package com.example.jwt.appilication.dto;

import java.util.Date;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 *  Dùng để trả về khi gặp các lỗi 403 - 500 v.v...
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
public class HttpResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date timeStamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String message;

}
