package com.example.f21g3_smartgroceryshopping.response;

/**
 * The subclass of LoadResponse. Providing it is used with LiveData indicating that the last request for the given livedata resulted in error
 * @param <Response>
 */
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
