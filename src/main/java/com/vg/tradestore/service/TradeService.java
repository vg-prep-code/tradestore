package com.vg.tradestore.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vg.tradestore.entity.Trade;
import com.vg.tradestore.exception.TradeException;
import com.vg.tradestore.repository.TradeRepository;

import jakarta.transaction.Transactional;

@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	
	public List<Trade> retrieveTradeList() throws TradeException {
		List<Trade> tradeList = tradeRepository.findAll();
		return tradeList;
	}

	public List<Trade> retrieveTradeList(String tradeId) throws TradeException {
		List<Trade> tradeList = tradeRepository.findByTradeId(tradeId);
		return tradeList;
	}

	public Optional<Trade> retrieveTrade(String tradeId, Integer version) throws TradeException {
		Optional<Trade> trade = tradeRepository.findByTradeIdAndVersion(tradeId, version);
		return trade;
	}

	public Integer retrieveLatestTradeVersion(String tradeId) throws TradeException {
		List<Trade> tradeList = tradeRepository.findByTradeId(tradeId);
		Optional<Integer> latestVersion = tradeList.stream().map(t -> t.getVersion())
				.max(Comparator.comparingInt(v -> v));
		if (latestVersion.isEmpty())
			return 0;
		else
			return latestVersion.get();
	}

	@Transactional
	public Trade addOrUpdateTrade(Trade trade) throws TradeException {

		if (trade.getMaturityDate().isBefore(LocalDate.now())) {
			throw new TradeException("Trade with maturity date less than today's date not allowed.");
		}

		List<Trade> tradeList = retrieveTradeList(trade.getTradeId());
		if (tradeList.isEmpty()) {
			tradeRepository.save(trade);
		} else {
			if (retrieveLatestTradeVersion(trade.getTradeId()) > trade.getVersion()) {
				throw new TradeException("Lower version received, Trade Rejected.");
			} else {
				tradeRepository.save(trade);
			}
		}
		return trade;

	}

}
