package com.example.f21g3_smartgroceryshopping.response;

public class RepositoryResponse<Response> {

    private Response response;
    private Throwable error;

    public RepositoryResponse(Response response) {
        this.response = response;
    }

    public RepositoryResponse(Throwable error) {
        this.error = error;
    }

    public RepositoryResponse(Response response, Throwable error) {
        this.response = response;
        this.error = error;
    }

    public Response getResponse() {
        return response;
    }

    public Throwable getError() {
        return error;
    }

}
