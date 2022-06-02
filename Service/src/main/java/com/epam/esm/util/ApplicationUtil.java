package com.epam.esm.util;

public class ApplicationUtil {
    public static Integer calculateOffset(Integer pageSize, Integer page){
        return pageSize*(page - 1);
    }
}
