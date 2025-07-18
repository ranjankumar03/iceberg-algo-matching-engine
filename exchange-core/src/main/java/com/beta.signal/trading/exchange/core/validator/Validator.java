package com.beta.signal.trading.exchange.core.validator;

import com.beta.signal.trading.exchange.core.exception.ExchangeException;

public interface Validator<O> {

    <O> void validate(O order) throws ExchangeException;
}

