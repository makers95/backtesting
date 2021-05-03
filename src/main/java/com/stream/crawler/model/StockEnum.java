package com.stream.crawler.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Param $
 * @return $
 **/

public enum StockEnum {
    taiex_total_return("00","台灣加權指數含息"),
    taiex("01","台灣加權指數");


    @Getter
    @Setter
    String stockCode;
    String name;

    StockEnum(String stockCode, String name) {
        this.stockCode = stockCode;
        this.name = name;
    }

}
