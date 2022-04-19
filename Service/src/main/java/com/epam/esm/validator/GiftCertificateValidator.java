package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class GiftCertificateValidator  {
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_DESCRIPTION_LENGTH = 300;
    private static final int MIN_DESCRIPTION_LENGTH = 3;
    private static final int MIN_DURATION_VALUE = 1;
    private static final int MAX_DURATION_VALUE = 365;
    private static final BigDecimal MIN_PRICE_VALUE = BigDecimal.ONE;
    private static final BigDecimal MAX_PRICE_VALUE = BigDecimal.valueOf(10000);
    private static final Set<String> AVAILABLE_SORT_ORDERS = Set.of("asc", "desc");
    private static final Set<String> AVAILABLE_FIELDS = Set.of("name", "duration", "price", "create_date");

    public boolean isValid(GiftCertificate giftCertificate) {
        return isNameValid(giftCertificate.getName()) &&
                isDescriptionValid(giftCertificate.getDescription()) &&
                isPriceValid(giftCertificate.getPrice()) &&
                isDurationValid(giftCertificate.getDuration());
    }

    public boolean isNameValid(String name) {
        return name != null &&
                name.length() >= MIN_NAME_LENGTH &&
                name.length() <= MAX_NAME_LENGTH;
    }

    public boolean isDescriptionValid(String description) {
        return description != null &&
                description.length() >= MIN_DESCRIPTION_LENGTH &&
                description.length() <= MAX_DESCRIPTION_LENGTH;
    }

    public boolean isPriceValid(BigDecimal price) {
        return price != null &&
                price.compareTo(MIN_PRICE_VALUE) >= 0 &&
                price.compareTo(MAX_PRICE_VALUE) <= 0;
    }

    public boolean isDurationValid(Integer duration) {
        return duration != null &&
                duration >= MIN_DURATION_VALUE &&
                duration <= MAX_DURATION_VALUE;
    }
    public boolean isGiftCertificateFieldListValid(String fieldList) {
        return fieldList == null || AVAILABLE_FIELDS.contains(fieldList);
    }

    public boolean isOrderSortValid(String orderSort) {
        return orderSort == null || AVAILABLE_SORT_ORDERS.contains(orderSort.toLowerCase());
    }


}
