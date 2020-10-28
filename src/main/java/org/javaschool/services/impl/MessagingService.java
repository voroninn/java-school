package org.javaschool.services.impl;

import org.javaschool.entities.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingService {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(ScheduleEntity schedule) {
        jmsTemplate.send(session -> session.createObjectMessage(schedule));
    }
}