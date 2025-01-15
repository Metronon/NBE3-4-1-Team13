package com.ll.coffee.global;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

/**
 * @author shjung
 * @since 25. 1. 14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class RsData<T> {
    @NonNull
    private final String resultCode;

    @NonNull
    private final String msg;

    @NonNull
    private final T data;

    public RsData(String resultCode, String msg) {
        this(resultCode, msg, (T) new Empty());
    }

    @JsonIgnore
    public int getStatusCode() {
        return Integer.parseInt(resultCode.split("-")[0]);
    }
}