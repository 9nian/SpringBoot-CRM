package com.gb.crm.commons.utils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){

        //replace("-","") --->将"-"转为"";
        return UUID.randomUUID().toString().replace("-","");
    }
}
