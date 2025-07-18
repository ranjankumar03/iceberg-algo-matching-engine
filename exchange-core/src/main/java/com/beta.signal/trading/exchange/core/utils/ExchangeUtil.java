package com.beta.signal.trading.exchange.core.utils;

import com.beta.signal.trading.exchange.core.constants.ExchangeConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility class for exchange-related operations.
 * <p>
 * This class provides methods to validate if a given string is a digit or numeric.
 * </p>
 */
public class ExchangeUtil {

    public static boolean isDigit(String strNum) {

        return StringUtils.isNotEmpty(strNum) && strNum.matches(ExchangeConstants.DIGIT_REGEX);
    }

    public static boolean isNumeric(String strNum) {

        return StringUtils.isNotEmpty(strNum) && strNum.matches(ExchangeConstants.NUMERIC_REGEX);
    }
}
