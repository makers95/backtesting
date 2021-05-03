package com.stream.crawler.parse;

import com.stream.crawler.model.StockEnum;
import com.stream.crawler.model.StockDetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.Locale;

public class parse_2 {
    public parse_2() {
    }

    public static ArrayList<StockDetail> parse(String source) throws ParseException {

        Document doc = Jsoup.parse(source);
        Elements tds = doc.select("td");

        ArrayList<StockDetail> stocks = new ArrayList<>();

        for (int i = 5; i < tds.size(); i = i + 5) {
            //跳過標題
            StockDetail StockDetail = new StockDetail();

            //原始資料
            String column_date = tds.get(i).text();
            String column_priceOpen = tds.get(i+1).text();
            String column_priceHigh = tds.get(i+2).text();
            String column_priceLow = tds.get(i+3).text();
            String column_priceEnd = tds.get(i+4).text();

            //處理日期
            Chronology chrono = MinguoChronology.INSTANCE;
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                    .appendPattern("yyy/MM/dd").toFormatter().withChronology(chrono)
                    .withDecimalStyle(DecimalStyle.of(Locale.getDefault()));
            ChronoLocalDate taiwan_date = chrono.date(df.parse(column_date));
            LocalDate localDate = LocalDate.from(taiwan_date);

            StockDetail.setDate(localDate);
            Float price = Float.parseFloat(column_priceOpen.replace(",", ""));
            StockDetail.setName("TAIEX");
            StockDetail.setSId(localDate.toString().replace("-","")+ StockEnum.taiex.getStockCode());
            StockDetail.setPrice_open(Float.parseFloat(column_priceOpen.replace(",", "")));
            StockDetail.setPrice_end(Float.parseFloat(column_priceEnd.replace(",", "")));
            StockDetail.setPrice_high(Float.parseFloat(column_priceHigh.replace(",", "")));
            StockDetail.setPrice_low(Float.parseFloat(column_priceLow.replace(",", "")));

            stocks.add(StockDetail);
        }
        return stocks;
    }

    public static void main(String[] args) {
        double random = Math.random();
        System.out.println(random*100+1);

    }


}
