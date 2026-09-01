package com.mypack.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RenameConversationRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    public RenameConversationRequest() {
    }

    public RenameConversationRequest(String title) {
        this.title = title;
    }

    // Yeh getter method missing hone par error aati hai
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}