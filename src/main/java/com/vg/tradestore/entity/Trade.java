package com.vg.tradestore.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRADE")
@IdClass(TradeId.class)
@Data
@NoArgsConstructor
public class Trade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TradeId", nullable = false)
	private String tradeId;

	@Id
	@Column(name = "Version", nullable = false)
	private Integer version;

	@Column(name = "CounterPartyId", nullable = false)
	private String counterPartyId;

    @Column(name = "BookId", nullable = false)
	private String bookId;

    @Column(name = "MaturityDate", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate maturityDate;

    @Column(name = "CreatedDate", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate createdDate;

    @Column(name = "Expired", nullable = false)
	private String expired;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		return Objects.equals(bookId, other.bookId) && Objects.equals(counterPartyId, other.counterPartyId)
				&& Objects.equals(createdDate, other.createdDate) && Objects.equals(expired, other.expired)
				&& Objects.equals(maturityDate, other.maturityDate) && Objects.equals(tradeId, other.tradeId)
				&& Objects.equals(version, other.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, counterPartyId, createdDate, expired, maturityDate, tradeId, version);
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", version=" + version + ", counterPartyId=" + counterPartyId + ", bookId="
				+ bookId + ", maturityDate=" + maturityDate + ", createdDate=" + createdDate + ", expired=" + expired
				+ "]";
	}
    
}
