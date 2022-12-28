package com.course.kafka.kafka.broker.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderReplyMessage {
    private String replyMessage;
}
