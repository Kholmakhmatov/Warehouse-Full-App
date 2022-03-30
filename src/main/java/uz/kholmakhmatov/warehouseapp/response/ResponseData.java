package uz.kholmakhmatov.warehouseapp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseData {
    private String message;
    private boolean success;
    private Object data;
    private long timestamp;

    public ResponseData(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseData(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseData() {
    }
}
