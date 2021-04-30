/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/

package com.nosto.ovalextras.constraint;

import ch.digitalfondue.vatchecker.EUVatCheckResponse;
import ch.digitalfondue.vatchecker.EUVatChecker;
import com.google.common.collect.ImmutableSet;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

public class VatIdCheck extends AbstractAnnotationCheck<VatId> {

    @Override
    public boolean isSatisfied(Object validatedObject, Object value, ValidationCycle validator) throws OValException {
        if (value == null) {
            return false;
        } else {
            return isValid(value.toString());
        }
    }

    private boolean isValid(String vat) {
        if (vat.isEmpty()) {
            return false;
        }

        String countryVatCode = vat.substring(0, 2);
        if (!CountryUtils.isEuCountry(countryVatCode)) {
            return true;
        }

        String vatNumber = vat.substring(2);
        return validateEUVat(countryVatCode, vatNumber);
    }

    private boolean validateEUVat(String vatCountryCode, String vatNumber) {
        EUVatCheckResponse resp = EUVatChecker.doCheck(vatCountryCode, vatNumber);
        return resp.isValid();
    }

    private static class CountryUtils {
        // Croatia 1st July 2013 onwards
        // UK left EU from 1st January 2021
        public static final ImmutableSet<String> VAT_CODES_FOR_EU_COUNTRIES =
                ImmutableSet.of("AT", "BE", "BG", "CY", "CZ", "DK", "EE", "FI", "FR", "DE", "EL", "HU", "IE", "IT",
                        "LV", "LT", "LU", "MT", "NL", "PL", "PT", "RO", "SK", "SI", "ES", "SE", "HR");


        @SuppressWarnings({"BooleanMethodIsAlwaysInverted"})
        public static boolean isEuCountry(String country) {
            if (country == null) {
                return false;
            }
            return VAT_CODES_FOR_EU_COUNTRIES.contains(country.toUpperCase());
        }
    }

}
