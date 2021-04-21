package com.stream.crawler.parse;


import com.stream.backtesting.model.stock;
import com.stream.backtesting.model.stockEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class parse_1 {
    public parse_1() {
    }

    public static ArrayList<stock> parseData_1(String source) throws ParseException {

        Document doc = Jsoup.parse(source);
        Elements tds = doc.select("td");

        ArrayList<stock> stocks = new ArrayList<>();

        for (int i = 0; i < tds.size(); i = i + 2) {
            stock stock = new stock();
            String skip = "日　期";
            String skip_1 = "發行量加權股價報酬指數";

            if (tds.get(i).text().contains(skip) || tds.get(i + 1).text().contains(skip_1)) {
                continue;
            }

            //原始資料
            String column_date = tds.get(i).text(); //yyy/mm/dd
            String column_price = tds.get(i + 1).text();

            //處理日期
            Chronology chrono = MinguoChronology.INSTANCE;
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                    .appendPattern("yyy/MM/dd").toFormatter().withChronology(chrono)
                    .withDecimalStyle(DecimalStyle.of(Locale.getDefault()));
            ChronoLocalDate taiwan_date = chrono.date(df.parse(column_date));
            LocalDate localDate = LocalDate.from(taiwan_date);

            Float price = Float.parseFloat(column_price.replace(",", ""));
            stock.setSId(localDate.toString().replace("-", "") + stockEnum.taiex_total_return.getStockCode());
            stock.setDate(localDate);
            stock.setName("TAIEX_TotalReturn");
            stock.setPrice(price);
            stocks.add(stock);
        }
        return stocks;
    }


}
