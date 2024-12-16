package com.example.wallet.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public record BalanceDto(BigDecimal balance) implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

}
