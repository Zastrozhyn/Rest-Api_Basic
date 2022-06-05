package com.epam.esm.util;

import java.util.ArrayList;
import java.util.List;

public class SqlQueryBuilder {
    public static final String DESC_SORT = " DESC";
    public static final String ASC_SORT = " ASC";
    public static final String CERTIFICATE_ID = " gift_certificate.id";
    private static final String EMPTY = "NULL";
    public static final String SEARCH_AND_SORT_QUERY_START = """
        SELECT gift_certificate.id, count(*), gift_certificate.name, description, price, 
        duration, create_date, last_update_date 
        FROM gift_certificate JOIN tag_certificate ON certificate_id=gift_certificate.id 
        JOIN tag ON tag_id=tag.id WHERE tag.name IN ('%s' """;
    private static final String FIND_TAG = ", '%s'";
    public static final String SEARCH_AND_SORT_QUERY_END = """
        ) OR (gift_certificate.name LIKE CONCAT ('%%', '%s', '%%') OR gift_certificate.description 
        LIKE CONCAT ('%%', '%s', '%%')) group by gift_certificate.id """;
    private static String HAVING_COUNT = " having count(*)=";
    private static final String ORDER_BY = " order by ";
    private static final String GET_TOTAL_COST = """
        SELECT sum(cost) FROM users JOIN orders on users.id = orders.user_id 
        WHERE user_id= %s group by users.id""";

    public static String buildCertificateQueryForSearchAndSort(List<String> tagList, String searchPart,
                                                               String sortingField, String orderSort) {
        if(tagList == null){
            tagList = new ArrayList<>();
            tagList.add(EMPTY);
            HAVING_COUNT = "";
        }
        else {
            HAVING_COUNT = HAVING_COUNT.concat(String.valueOf(tagList.size()));
        }

        if (sortingField == null) {
            sortingField = CERTIFICATE_ID;
        }

        if (orderSort == null){
            orderSort = ASC_SORT;
        }

        if (!orderSort.equalsIgnoreCase(DESC_SORT)){
            orderSort = ASC_SORT;
        }

        String SEARCH_AND_SORT_QUERY = SEARCH_AND_SORT_QUERY_START
                .concat(FIND_TAG.repeat(tagList.size()-1))
                .concat(SEARCH_AND_SORT_QUERY_END)
                .concat(HAVING_COUNT)
                .concat(ORDER_BY)
                .concat(sortingField)
                .concat(orderSort);
        searchPart = searchPart != null ? searchPart : EMPTY;
        tagList.add(searchPart);
        tagList.add(searchPart);
        String[] attributes = tagList.toArray(String[]::new);
        return String.format(SEARCH_AND_SORT_QUERY, attributes);
    }

    public static String buildTotalCostQuery(Long id){
        return String.format(GET_TOTAL_COST, id.toString());
    }
}
