/*package com.mypack.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Request successful", data, null);
    }
    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return new ApiResponse<>(false, message, null, errorCode);
    }
}*/
