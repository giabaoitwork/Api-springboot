package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.*;
import com.TMDT.api.Api.springboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order add(List<CartDetail> cartDetailIds, Order order, int addressId, int point) {
        List<CartDetail> cartDetails = new ArrayList<>();
        for (CartDetail cartDetail : cartDetailIds) {
            cartDetails.add(cartService.get(cartDetail.getId()));
        }

        Customer customer = customerRepository.findById(order.getCustomer().getId()).get();
        customer.setPoint(customer.getPoint() - point);
        customerRepository.save(customer);
        Address address = addressRepository.findById(addressId);

        order.setAddress(address.getSubAddress() + ", " + address.getWardValue() + ", " + address.getDistrictValue() + ", " + address.getProvinceValue());
        order.setCustomer(customer);
        order.setCreateDate(LocalDateTime.now());
        order.setDeliveryId(order.getDeliveryId());
        order.setDiscount(point * 1000);
        order.setPaymentDate(LocalDateTime.now());
        order.setPaymentStatus(1);
        order.setTotal(cartService.calculateTotalAmount(cartDetails));
        order.setStatus(1);

        orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setStatus(1);
            orderDetail.setPrice((int) (cartDetail.getProduct().getPrice() * cartDetail.getQuantity()));
            orderDetail.setPhoneCategory(cartDetail.getPhoneCategory());
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
        order.setOrderDetails(orderDetails);
        cartRepository.deleteAll(cartDetailIds);

        return clearProperty(order);
    }

    public Order clearProperty(Order order) {
//        order.setCustomer(null);
        order.getCustomer().setOrders(null);
        order.getCustomer().setCartDetails(null);
        order.getCustomer().setAddresses(null);
        order.getCustomer().setOrders(null);
        order.getCustomer().setPassword(null);
        order.getOrderDetails().forEach(orderDetail -> {
            orderDetail.setOrder(null);
            productService.clearProperty(orderDetail.getProduct());
            if (orderDetail.getPhoneCategory() != null) {
                orderDetail.getPhoneCategory().setProductPhoneCategories(null);
            }
        });
        System.out.println(order);
        return order;
    }

    public List<Order> clearProperties(List<Order> orders) {
        for (Order order : orders) {
            clearProperty(order);
        }
        return orders;
    }
}
