package com.example.f21g3_smartgroceryshopping.response;

public class SuccessLoadResponse<Response> extends LoadResponse<Response> {

    public SuccessLoadResponse(Response response) {
        super(response);
    }

    public SuccessLoadResponse(Throwable error) {
        super(error);
    }

    public SuccessLoadResponse(Response response, Throwable error) {
        super(response, error);
    }

}
