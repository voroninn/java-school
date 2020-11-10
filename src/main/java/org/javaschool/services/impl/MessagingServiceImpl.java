package org.javaschool.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessagingServiceImpl implements org.javaschool.services.interfaces.MessagingService {

    private final JmsTemplate jmsTemplate;

    @Override
    public void sendMessage() {
        jmsTemplate.send(session -> session.createTextMessage("Update timetable"));
        log.info("Message sent to queue");
    }
}