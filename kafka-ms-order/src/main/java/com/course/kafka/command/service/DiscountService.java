package com.course.kafka.command.service;

import com.course.kafka.api.request.DiscountRequest;
import com.course.kafka.api.request.PromotionRequest;
import com.course.kafka.command.action.DiscountAction;
import com.course.kafka.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    private DiscountAction action;

    public void createPromotion(DiscountRequest request){
        action.publishToKafka(request);
    }
}
