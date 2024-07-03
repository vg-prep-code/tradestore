package com.vg.tradestore.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TradeScheduledTask {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
	public void updateColumnBasedOnCondition() {
		String updateQuery = "UPDATE trade SET expired = 'Y' WHERE maturity_date < now()";
		jdbcTemplate.update(updateQuery);
		log.info("Scheduled update executed successfully.");
	}
}
