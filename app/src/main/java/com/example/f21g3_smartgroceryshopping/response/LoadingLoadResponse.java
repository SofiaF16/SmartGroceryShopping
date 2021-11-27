package com.example.f21g3_smartgroceryshopping.response;

/**
 * The subclass of LoadResponse. Providing it is used with LiveData indicating that the last request for the given livedata is in the process of loading
 * @param <Response>
 */
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
