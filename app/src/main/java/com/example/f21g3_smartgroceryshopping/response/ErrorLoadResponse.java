package com.example.f21g3_smartgroceryshopping.response;

public class ErrorLoadResponse<Response> extends LoadResponse<Response> {

    public ErrorLoadResponse(Response response) {
        super(response);
    }

    public ErrorLoadResponse(Throwable error) {
        super(error);
    }

    public ErrorLoadResponse(Response response, Throwable error) {
        super(response, error);
    }

}
