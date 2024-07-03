package com.vg.tradestore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vg.tradestore.entity.Trade;
import com.vg.tradestore.entity.TradeId;

@Repository
public interface TradeRepository extends JpaRepository<Trade, TradeId> {

	Optional<Trade> findByTradeIdAndVersion(String tradeId, Integer version);

	List<Trade> findByTradeId(String tradeId);

}
