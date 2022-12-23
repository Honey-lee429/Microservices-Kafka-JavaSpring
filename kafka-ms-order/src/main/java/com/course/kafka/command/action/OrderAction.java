package com.course.kafka.command.action;

import com.course.kafka.api.request.OrderItemRequest;
import com.course.kafka.api.request.OrderRequest;
import com.course.kafka.broker.message.OrderMessage;
import com.course.kafka.broker.producer.OrderProducer;
import com.course.kafka.entity.Order;
import com.course.kafka.entity.OrderItem;
import com.course.kafka.repository.OrderItemRepository;
import com.course.kafka.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;

@Component  //or @Service
public class OrderAction {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    public void saveToDatabase(Order order) {
        orderRepository.save(order);
        order.getItems().forEach(orderItemRepository::save);
    }



    public Order convertToOrder(OrderRequest orderRequest) {
        var result = new Order();
        result.setCredtCardNumber(orderRequest.getCreditCardNumber());
        result.setOrderLocation(orderRequest.getOrderLocation());
        result.setOrderDateTime(LocalDateTime.now());
        result.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        //We need to map OrderItemRequest from OrderRequest, into OrderItem which is JPA entity
        var items = orderRequest.getItemRequests().stream().map(this::convertToOrderItem).collect(Collectors.toList());
        //then for each OrderItem, we need to maintain JPA OneToMany ralationship by settin the order
        items.forEach(item -> item.setOrder(result));
        //and vice versa, we set list of order item as item list in resulting Order. This is a common implementation for a JPA relationship
        result.setItems(items);

        return result;
    }

    private OrderItem convertToOrderItem(OrderItemRequest orderItemRequest) {
        var result = new OrderItem();
        result.setItemName(orderItemRequest.getItemName());
        result.setPrice(orderItemRequest.getPrice());
        result.setQuantity(orderItemRequest.getQuantity());
        return result;
    }


    public void publishToKafka(OrderItem orderItem) {
        var oderMessage = new OrderMessage();
        oderMessage.setItemName(orderItem.getItemName());
        oderMessage.setPrice(orderItem.getPrice());
        oderMessage.setQuantity(orderItem.getQuantity());

        var oder = orderItem.getOrder();
        oderMessage.setOrderDateTime(oder.getOrderDateTime());
        oderMessage.setOrderLocation(oder.getOrderLocation());
        oderMessage.setOrderNumber(oder.getOrderNumber());
        oderMessage.setCreditCardNumber(oder.getCredtCardNumber());

        orderProducer.publish(oderMessage);
    }
}
