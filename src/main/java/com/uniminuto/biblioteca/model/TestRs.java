
package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class TestRs {

    private int status;
    private String message;

    // Getter y Setter para status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Getter y Setter para message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
