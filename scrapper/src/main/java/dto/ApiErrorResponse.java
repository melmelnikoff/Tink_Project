package dto;

public class ApiErrorResponse {
    private String error;

    public ApiErrorResponse(int value, String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}