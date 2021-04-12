package com.community.config;

import okhttp3.internal.connection.Exchange;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.*;

import static com.community.utils.MqConstant.*;

@Configuration
public class RabbitmqConfig {

    /** 邮件 **/
    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_REGISTER_QUEUE, true, false, false, null);
    }

    @Bean
    public TopicExchange mailExchange() {
        return new TopicExchange(MAIL_REGISTER_EXCHANGE, true, false, null);
    }

    @Bean
    public Binding orderBinding() {
        return new Binding(MAIL_REGISTER_QUEUE, Binding.DestinationType.QUEUE, MAIL_REGISTER_EXCHANGE,
            MAIL_REGISTER_ROUTING_KEY, null);
    }

    /** json输出 **/
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }




//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.Exchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//    @Configuration
//    public class RabbitmqConfig {
//
//        /** 邮件 **/
//        @Bean
//        public Queue mailQueue() {
//            return new Queue(MAIL_REGISTER_QUEUE, true, false, false, null);
//        }
//
//        @Bean
//        public Exchange mailExchange() {
//            return new TopicExchange(MAIL_REGISTER_EXCHANGE, true, false, null);
//        }
//
//        @Bean
//        public Binding orderBinding() {
//            return new Binding(MAIL_REGISTER_QUEUE, Binding.DestinationType.QUEUE, MAIL_REGISTER_EXCHANGE,
//                    MAIL_REGISTER_ROUTING_KEY, null);
//        }
//
//        /** send grid **/
//        @Bean
//        public Queue sendGridQueue() {
//            return new Queue(MAIL_SEND_GRID_QUEUE, true, false, false, null);
//        }
//
//        @Bean
//        public Exchange sendGridExchange() {
//            return new TopicExchange(MAIL_SEND_GRID_EXCHANGE, true, false, null);
//        }
//
//        @Bean
//        public Binding sendGridBinding() {
//            return new Binding(MAIL_SEND_GRID_QUEUE, Binding.DestinationType.QUEUE, MAIL_SEND_GRID_EXCHANGE,
//                    MAIL_SEND_GRID_ROUTING_KEY, null);
//        }
//
//        /** json输出 **/
//        @Bean
//        public MessageConverter messageConverter() {
//            return new Jackson2JsonMessageConverter();
//        }
//    }

}
