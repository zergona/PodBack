package com.thesis.redomat.data.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationDto {
    private int requestId;
    private int response;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public ConfirmationDto(int requestId, int response) {
        this.requestId = requestId;
        this.response = response;
    }
}
