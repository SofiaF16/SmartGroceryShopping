package com.example.f21g3_smartgroceryshopping.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.f21g3_smartgroceryshopping.repository.MainRepository;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.RepositoryResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.Order;
import com.example.f21g3_smartgroceryshopping.service.entity.OrderItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderWithOrderItems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrdersHistoryViewModel extends ViewModel {

    private static final long UPDATE_CART_LOADING = 101;
    private static final long SUCCESS_CART_LOADING = 201;
    private static final long UPDATE_CART_ERROR = -101;

    private final MutableLiveData<LoadResponse<List<Order>>> ordersResponse = new MutableLiveData<>();
    private final MutableLiveData<LoadResponse<Long>> updateCartResponse = new MutableLiveData<>();

    private final MainRepository mainRepository;

    @Inject
    public OrdersHistoryViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }


    public LiveData<LoadResponse<List<Order>>> getOrderResponse() {
        return ordersResponse;
    }

    public LiveData<LoadResponse<Long>> getUpdateCartResponse() {
        return updateCartResponse;
    }

    public void loadOrders() {
        CompletableFuture.runAsync(() -> {
            RepositoryResponse<List<StorageOrderWithOrderItems>> allOrders = mainRepository.getAllOrders();
            LoadResponse<List<Order>> listLoadResponse = prepareResponse(allOrders);
            ordersResponse.postValue(listLoadResponse);
        });
    }

    private LoadResponse<List<Order>> prepareResponse(RepositoryResponse<List<StorageOrderWithOrderItems>> allOrders) {
        if(allOrders.getError() == null && allOrders.getResponse() != null) {
            List<Order> orders = toListOrder(allOrders.getResponse());
            return new SuccessLoadResponse<>(orders);
        }

        return new ErrorLoadResponse<>(allOrders.getError());
    }

    private List<Order> toListOrder(List<StorageOrderWithOrderItems> storageOrders) {
        List<Order> orders = new ArrayList<>();

        for (StorageOrderWithOrderItems storageOrder: storageOrders) {
            Order order = toOrder(storageOrder);
            orders.add(order);
        }

        return orders;
    }

    private Order toOrder(StorageOrderWithOrderItems storageOrder) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (StorageOrderItem storageOrderItem: storageOrder.orderItems) {
            OrderItem orderItem = toOrderItem(storageOrderItem);
            orderItems.add(orderItem);
        }

        return new Order(storageOrder.storageOrder.orderId, storageOrder.storageOrder.orderDate, orderItems);
    }

    private OrderItem toOrderItem(StorageOrderItem storageOrderItem) {
        return new OrderItem(storageOrderItem.fk_order, storageOrderItem.dishTitle, storageOrderItem.portions);
    }

    public void updateCartWith(final Order order) {
        CompletableFuture.runAsync(() -> {
            updateCartResponse.postValue(new LoadingLoadResponse<>(UPDATE_CART_LOADING));
            mainRepository.deleteAllCartItems();

            List<StorageCurrentCartItem> storageCurrentCartItems = toStorageCurrentCartItems(order);
            long[] longs = mainRepository.addToCart(storageCurrentCartItems);

            if(longs.length != 0) {
                updateCartResponse.postValue(new SuccessLoadResponse<>(SUCCESS_CART_LOADING));
            } else {
                updateCartResponse.postValue(new ErrorLoadResponse<>(UPDATE_CART_ERROR));
            }
        });
    }

    private List<StorageCurrentCartItem> toStorageCurrentCartItems(Order order) {
        List<StorageCurrentCartItem> currentCartItems = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {
            currentCartItems.add(new StorageCurrentCartItem(orderItem.getDishId(), orderItem.getDishTitle(), orderItem.getPortions()));
        }

        return currentCartItems;
    }

}
