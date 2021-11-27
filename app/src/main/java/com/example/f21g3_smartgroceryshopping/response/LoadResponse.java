package com.example.f21g3_smartgroceryshopping.response;

/**
 * The basic class representing state the request to the backend or to the local storage is currently in. This class must be used with all LiveData in
 * viewmodels so any subscriber to live data could know the current state
 */
public abstract class LoadResponse<Response> {

    private Response response;
    private Throwable error;

    public LoadResponse(Response response) {
        this.response = response;
    }

    public LoadResponse(Throwable error) {
        this.error = error;
    }

    public LoadResponse(Response response, Throwable error) {
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
