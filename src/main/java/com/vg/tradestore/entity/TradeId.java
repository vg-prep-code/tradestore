package com.vg.tradestore.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TradeId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tradeId;

	private Integer version;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeId other = (TradeId) obj;
		return Objects.equals(tradeId, other.tradeId) && Objects.equals(version, other.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tradeId, version);
	}

	@Override
	public String toString() {
		return "TradeId [tradeId=" + tradeId + ", version=" + version + "]";
	}

}
