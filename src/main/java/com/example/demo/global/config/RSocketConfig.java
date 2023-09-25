package com.example.demo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class RSocketConfig {

//    @Bean
//    public RSocketMessageHandler rsocketMessageHandler() {
//        RSocketMessageHandler handler = new RSocketMessageHandler();
//        handler.setRouteMatcher(new PathPatternRouteMatcher());
//        return handler;
//    }

//    @Bean
//    public RSocketStrategies rsocketStrategies() {
//        return RSocketStrategies.builder()
//                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
//                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
//                .routeMatcher(new PathPatternRouteMatcher())
//                .build();
//    }
    @Bean
    public RSocketRequester getRSocketRequester(RSocketStrategies rSocketStrategies){
        return RSocketRequester.builder()
                .rsocketConnector(connector -> connector.reconnect(Retry.backoff(10, Duration.ofMillis(500))))
                .rsocketStrategies(rSocketStrategies)
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 6565);
    }
}
