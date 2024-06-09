package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.models.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    List<CartDetail> cartDetailIds;
    Order order;
    int addressId;
    int point;
}
