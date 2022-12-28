package com.course.kafka.broker.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DiscountMessage {

    private String messageDiscountCode;

}
