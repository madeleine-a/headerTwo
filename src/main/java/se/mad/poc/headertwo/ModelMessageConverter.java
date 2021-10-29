package se.mad.poc.headertwo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Slf4j
public class ModelMessageConverter extends SimpleMessageConverter {
    private ObjectMapper mapper;

    public ModelMessageConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @SneakyThrows
    @Override
    public Object fromMessage(javax.jms.Message message) throws JMSException, MessageConversionException {
        if (message.getStringProperty("businessid") == null) {
            log.warn("Business ID is missing on message");
        }
        String text = extractStringFromMessage((TextMessage) message);
        return mapper.readValue(text, Model.class);
    }
}

