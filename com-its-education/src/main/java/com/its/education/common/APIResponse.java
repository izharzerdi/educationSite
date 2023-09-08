package com.its.education.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public final class APIResponse<T> {

    private Integer status;
    private Error error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String warning;

    private APIResponse(Builder<T> builder) {
        this.status = builder.getStatus();
        this.error = builder.getError();
        this.data = builder.getData();
        this.warning = builder.getWarning();
    }

    public APIResponse() {
        this.status = HttpStatus.OK.value();
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }

        if (Objects.isNull(other)) {
            return false;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        var otherApiResponse = (APIResponse) other;

        return Objects.equals(this.status, otherApiResponse.status)
                && Objects.equals(this.error, otherApiResponse.error)
                && Objects.equals(this.data, otherApiResponse.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, data);
    }

    @Override
    public String toString() {
        return "<APIResponse STATUS: " + getStatus() + ", ERROR: " + getError() + ", DATA: " + getData() + ">";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    /**
     * The method is to append the new warning to the existing warning.
     *
     * @param newWarning
     */
    public void appendWarning(String newWarning) {
        if (StringUtils.isNotEmpty(newWarning)) {
            if (StringUtils.isEmpty(this.warning)) {
                this.warning = newWarning;
            } else {
                this.warning += ", " + newWarning;
            }
        }
    }

    /**
     * @param <T>
     * @param data
     * @return APIResponse<T>
     */
    public static <T> APIResponse<T> data(T data) {
        return new Builder<T>().setData(data).build();
    }

    /**
     * @param <T>
     * @param warning
     * @return APIResponse<T>
     */
    public static <T> APIResponse<T> warning(String warning) {
        return new Builder<T>().setWarning(warning).build();
    }

    /**
     * @param <T>
     * @param data
     * @param warning
     * @return APIResponse<T>
     */
    public static <T> APIResponse<T> dataAndWarning(T data, String warning) {
        return new Builder<T>(data, warning).build();
    }

    /**
     * @param <T>
     * @param status
     * @param error
     * @return APIResponse<T>
     */
    public static <T> APIResponse<T> statusAndError(Integer status, Error error) {
        return new Builder<T>(status, error).build();
    }

    /**
     * @param <T>
     * @param status
     * @param error
     * @param data
     * @param warning
     * @return APIResponse<T>
     */
    public static <T> APIResponse<T> statusErrorDataAndWarning(Integer status, Error error, T data, String warning) {
        return new Builder<T>(status, error, data, warning).build();
    }

    /**
     * Builder class for API Response.
     *
     * @author Laerdal
     *
     * @param <T>
     */
    private static class Builder<T> {
        private Integer status;
        private Error error;
        private T data;
        private String warning;

        public Builder() {
            this.setStatus(HttpStatus.OK.value());
            this.setError(new Error());
        }

        public Builder(T data, String warning) {
            this();
            this.data = data;
            this.warning = warning;
        }

        public Builder(Integer status, Error error) {
            this.status = status;
            this.error = error;
        }

        public Builder(Integer status, Error error, T data, String warning) {
            this.status = status;
            this.error = error;
            this.data = data;
            this.warning = warning;
        }

        public Integer getStatus() {
            return status;
        }

        public Builder<T> setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Error getError() {
            return error;
        }

        public Builder<T> setError(Error error) {
            this.error = error;
            return this;
        }

        public T getData() {
            return data;
        }

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public String getWarning() {
            return warning;
        }

        public Builder<T> setWarning(String warning) {
            this.warning = warning;
            return this;
        }

        /**
         * Build the APIResponse Object.
         *
         * @return APIResponse<T>
         */
        public APIResponse<T> build() {
            return new APIResponse<>(this);
        }

    }
}
