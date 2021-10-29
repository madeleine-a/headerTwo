package se.mad.poc.headertwo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.atg.lib.stacklog.annotation.LogExclude;
import se.atg.lib.stacklog.annotation.LogStack;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
@Slf4j
public class EventListener {
    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    public EventListener(RestTemplateBuilder builder, ObjectMapper mapper) {
        this.restTemplate = builder.build();
        this.mapper = mapper;
    }

    @JmsListener(destination = "the-line", containerFactory = "containerFactory")
    @LogStack
    public void receive(Model model) {
        HttpEntity<Model> entityReq = new HttpEntity(model);
        restTemplate.postForObject("http://localhost:8084/mailbox", entityReq, String.class);
    }
}
