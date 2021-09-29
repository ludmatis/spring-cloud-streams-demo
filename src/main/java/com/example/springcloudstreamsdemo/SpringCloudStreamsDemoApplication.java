package com.example.springcloudstreamsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringCloudStreamsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamsDemoApplication.class, args);
    }


    @Bean
    public Consumer<String> toUpper() {
        return x -> System.out.println(x.toUpperCase());

    }

    @Bean
    public Consumer<String> okayBoss() {
        return x -> System.out.println(x);
    }

    @Bean
    public Function<String, String> toReverse() {
        return x -> new StringBuilder(x).reverse().toString();
    }

    @Bean
    public Supplier<Flux<String>> sendMessages() throws InterruptedException {
        final boolean[] yes = {true};
        return () -> Flux.fromStream(Stream.generate(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(10000);
                    return "drugi strumyk";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }
        })).subscribeOn(Schedulers.elastic()).share();
    }

    @Bean
    public Supplier<Flux<String>> sendMessagesTwo() {
        return () -> Flux.fromStream(Stream.generate(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(1000);
                    return "pierwszy supplier";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        })).subscribeOn(Schedulers.elastic()).share();
    }
}
