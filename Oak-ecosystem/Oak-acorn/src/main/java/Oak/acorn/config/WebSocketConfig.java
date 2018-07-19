package Oak.acorn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by Oak on 2018/7/5.
 * Description: webSocket 配置类
 */
@Configuration
@EnableWebSocketMessageBroker   //注解表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

    /**
     * 注册STOMP端点 并映射指定的url
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/endpointAric")
        .withSockJS();   //使用SockJS协议
    }

    /**
     *
     * 配置消息代理
     * 广播式
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry.enableSimpleBroker("/topic");
//        registry.setApplicationDestinationPrefixes("/app");
    }
}
