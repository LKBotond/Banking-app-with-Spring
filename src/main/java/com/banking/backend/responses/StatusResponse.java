package com.banking.backend.responses;

import lombok.Data;

@Data
public class StatusResponse {
    boolean status;

    public StatusResponse(boolean status) {
        this.status = status;
    }

}
