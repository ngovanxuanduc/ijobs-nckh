package com.immortal.internship.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T error;

    public static <T> Builder ok(T t){
        return new Builder().data(t,false);
    }

    public static <T> Builder error(T err){
        return new Builder().data(err,true);
    }

    private BaseResponse(Builder<T> builder){
        if (builder.isError){
            this.error = builder.data;
        } else {
            this.data = builder.data;
        }
    }

    public static class Builder<T>{
        private T data;
        private boolean isError;


        public Builder data(T data, boolean isError){
            this.data = data;
            this.isError = isError;
            return this;
        }

        public BaseResponse build(){
            return new BaseResponse(this);
        }
    }

}
