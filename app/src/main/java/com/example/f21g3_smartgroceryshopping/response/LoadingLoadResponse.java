package com.example.f21g3_smartgroceryshopping.response;

public class LoadingLoadResponse<Response> extends LoadResponse<Response>{

    public LoadingLoadResponse(Response response) {
        super(response);
    }

    public LoadingLoadResponse(Throwable error) {
        super(error);
    }

    public LoadingLoadResponse(Response response, Throwable error) {
        super(response, error);
    }

}
