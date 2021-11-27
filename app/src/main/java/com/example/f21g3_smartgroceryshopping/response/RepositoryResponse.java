package com.example.f21g3_smartgroceryshopping.response;

/**
 * Generic class used to receive the response from backend or local storage as well as the error object in case some issues arised during conducting
 * of the operation
 */
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
