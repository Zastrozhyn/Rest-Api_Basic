package com.epam.esm.util;

public class SqlQueryBuilder {
    public static final String DESC_SORT = "DESC";
    public static final String CERTIFICATE_ID = " gift_certificate.id";
    private static final String EMPTY = "NULL";
    public static final String SEARCH_AND_SORT_QUERY = "SELECT gift_certificate.id, gift_certificate.name" +
            ", description, price, duration, create_date, last_update_date, tag.id AS tag_id, tag.name AS tag_name " +
            "FROM gift_certificate " +
            " JOIN tag_certificate ON certificate_id=gift_certificate.id " +
            "JOIN tag ON tag_id=tag.id " +
            "WHERE tag.name LIKE CONCAT ('%%', '%s', '%%') OR (gift_certificate.name LIKE CONCAT ('%%', '%s', '%%') " +
            "OR gift_certificate.description LIKE CONCAT ('%%', '%s', '%%')) ORDER BY ";

    public static String buildCertificateQueryForSearchAndSort(String tagName, String searchPart,
                                                               String sortingField, String orderSort) {
        tagName = tagName != null ? tagName : EMPTY;
        searchPart = searchPart != null ? searchPart : EMPTY;
        String queryMainPart = String.format(SEARCH_AND_SORT_QUERY, tagName, searchPart, searchPart);
        StringBuilder resultQuery = new StringBuilder(queryMainPart);

        if (sortingField != null && !sortingField.isEmpty()) {
             resultQuery.append(sortingField);
        } else {
            resultQuery.append(CERTIFICATE_ID);
        }
        if (orderSort != null && orderSort.equalsIgnoreCase(DESC_SORT)){
            resultQuery.append(" ").append(DESC_SORT);
        }
        return resultQuery.toString();
    }
}
