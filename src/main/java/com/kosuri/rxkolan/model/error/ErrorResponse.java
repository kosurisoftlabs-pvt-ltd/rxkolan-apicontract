package com.kosuri.rxkolan.model.error;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Boolean success;
    private List<Error> errorList;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error {
        private String key;
        private String cause;
        private String errorMessage;
        private String errorCode;
        private List<String> errorStackTrace;
    }
}