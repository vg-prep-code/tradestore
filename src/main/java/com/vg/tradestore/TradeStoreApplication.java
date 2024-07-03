package com.vg.tradestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.vg.tradestore"})
@EnableScheduling
public class TradeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeStoreApplication.class, args);
	}

}
