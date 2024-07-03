package com.vg.tradestore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vg.tradestore.entity.Trade;
import com.vg.tradestore.exception.TradeException;
import com.vg.tradestore.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

	@InjectMocks
	private TradeService tradeService;

	@Mock
	private TradeRepository tradeRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRetrieveTradeList() throws TradeException {
		List<Trade> expectedTradeList = Arrays.asList(buildTrade());
		when(tradeRepository.findByTradeId(anyString())).thenReturn(expectedTradeList);
		List<Trade> actualTradeList = tradeService.retrieveTradeList("T1");
		assertEquals(expectedTradeList, actualTradeList);
	}

	@Test
	public void testRetrieveTrade() throws TradeException {
		Trade expectedTrade = buildTrade();
		when(tradeRepository.findByTradeIdAndVersion(anyString(), anyInt())).thenReturn(Optional.of(expectedTrade));
		Optional<Trade> actualTrade = tradeService.retrieveTrade("T1", 1);
		assertTrue(actualTrade.isPresent());
		assertEquals(expectedTrade, actualTrade.get());
	}

	@Test
	public void testRetrieveLatestTradeVersion() throws TradeException {
		List<Trade> expectedTradeList = Arrays.asList(buildTrade());
		when(tradeRepository.findByTradeId(anyString())).thenReturn(expectedTradeList);
		Integer latestVersion = tradeService.retrieveLatestTradeVersion("T1");
		assertEquals(1, latestVersion);
	}

	@Test
	public void testAddOrUpdateTrade() throws TradeException {
		Trade trade = buildTrade();
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
		Trade savedTrade = tradeService.addOrUpdateTrade(trade);
		assertEquals(trade, savedTrade);
	}

	@Test
	public void testAddOrUpdateTrade_InvalidMaturityDate() throws TradeException {
		Trade trade = buildTrade();
		trade.setMaturityDate(LocalDate.now().minusDays(1));
		TradeException ex = assertThrows(TradeException.class, () -> tradeService.addOrUpdateTrade(trade));
		assertEquals("Trade with maturity date less than today's date not allowed.", ex.getMessage());
	}

	@Test
	public void testAddOrUpdateTrade_LowerVersion() throws TradeException {
		Trade trade = buildTrade();
		trade.setVersion(0);
		List<Trade> expectedTradeList = Arrays.asList(buildTrade());
		when(tradeRepository.findByTradeId(anyString())).thenReturn(expectedTradeList);
		TradeException ex = assertThrows(TradeException.class, () -> tradeService.addOrUpdateTrade(trade));
		assertEquals("Lower version received, Trade Rejected.", ex.getMessage());
	}

	private Trade buildTrade() {
		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(1);
		trade.setBookId("B1");
		trade.setCounterPartyId("CP1");
		trade.setCreatedDate(LocalDate.now());
		trade.setMaturityDate(LocalDate.now().plus(100, ChronoUnit.DAYS));
		trade.setExpired("N");
		return trade;
	}

}
