package com.vg.tradestore.controller;

import java.util.Comparator;
import java.util.List;

import org.aspectj.weaver.patterns.IScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vg.tradestore.entity.Trade;
import com.vg.tradestore.exception.TradeException;
import com.vg.tradestore.service.TradeService;

@RestController
@RequestMapping("/v1/tradestore")
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@GetMapping("/{tradeId}")
	public ResponseEntity<Trade> retrieveTrade(@PathVariable String tradeId) {

		try {
			List<Trade> tradeList = tradeService.retrieveTradeList(tradeId);
			if (tradeList == null || tradeList.isEmpty()) {
				throw new TradeException("TradeId is not present");
			}
			return ResponseEntity.ok(tradeList.stream()
					.sorted(Comparator.comparingInt(t -> ((Trade) t).getVersion()).reversed()).findFirst().get());
		} catch (Exception e ) {
			throw new TradeException(e.getMessage());
		}
	}

	@PostMapping("/trade")
	public ResponseEntity<Trade> addOrUpdateTrade(@RequestBody Trade trade) {
		try {
			Trade savedTrade = tradeService.addOrUpdateTrade(trade);
			return ResponseEntity.ok(savedTrade);
		} catch (Exception ex) {
			throw new TradeException(ex.getMessage());
	}
	}

}
