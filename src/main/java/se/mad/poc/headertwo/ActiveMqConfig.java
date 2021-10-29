package se.mad.poc.headertwo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.util.DestinationPathSeparatorBroker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ActiveMqConfig {

    @Bean
    public BrokerService broker(@Value("${spring.activemq.broker-url}") String url) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        BrokerPlugin[] plugins = new BrokerPlugin[1];
        plugins[0] = new DestinationPathSeparatorBroker();
        broker.setPlugins(plugins);
        broker.addConnector(url);
        return broker;
    }

    @Bean(name = "containerFactory")
    public DefaultJmsListenerContainerFactory containerFactory(ObjectMapper mapper,
                                                        @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
                                                        DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(messageConverter(mapper));
        return factory;
    }

    @Bean
    public ModelMessageConverter messageConverter(ObjectMapper mapper) {
        return new ModelMessageConverter(mapper);
    }
}
