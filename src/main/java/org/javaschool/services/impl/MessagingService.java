package org.javaschool.services.impl;

import lombok.extern.log4j.Log4j2;
import org.javaschool.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MessagingService {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(ScheduleDto scheduleDto) {
        jmsTemplate.send(session -> session.createObjectMessage(scheduleDto));
        log.info("Message sent to queue: " + scheduleDto);
    }
}