package com.stream.backtesting.save;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stream.backtesting.mapper.StockDetailMapper;
import com.stream.backtesting.mapper.StockMapper;
import com.stream.backtesting.model.stock;
import com.stream.backtesting.model.stockEnum;
import com.stream.backtesting.model.stock_detail;
import com.stream.crawler.parse.parse_1;
import com.stream.crawler.parse.parse_2;
import com.stream.crawler.read.get_1;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 抓取台灣加權指數含息資料
 * @Param $
 * @return $
 **/

@SpringBootTest
public class twse {

    @Resource
    private StockDetailMapper stockDetailMapper;

    @Test
    public void Test() throws Exception {
        String timeParam = "20210401";
        String url = "https://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date="+timeParam ;
        try {
        String source = get_1.doGet(url);

        //check repeat
        QueryWrapper<stock_detail> wrapper = new QueryWrapper<>();
        wrapper.likeRight("stockId",timeParam.substring(0,6));
        wrapper.likeLeft("stockId", stockEnum.taiex.getStockCode());
        List<stock_detail> stocks_old = stockDetailMapper.selectList(wrapper);
        Set<String> stockId_old = stocks_old.stream().map(stock -> stock.getSId()).collect(Collectors.toSet());

                ArrayList<stock_detail> stocks = new ArrayList<>();
                stocks = parse_2.parse(source);

                for (stock_detail stock : stocks) {
                    if(!stockId_old.contains(stock.getSId())){
                        System.out.println(stock);
                        stockDetailMapper.insert(stock);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //股市總加權指數
    @Test
    public void crawler() throws Exception {
        get_1 get_1 = new get_1();
        parse_2 parse_2 = new parse_2();
        String url = "https://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=";


        //產生參數陣列
        List<String> parameters = get_1.getParmaList(1999);
//        parameters.forEach(System.out::println);
//        List<String> parameters = new ArrayList<>();
//        parameters.add("20200601");
//        parameters.add("20200701");



        //check repeat
        QueryWrapper<stock_detail> wrapper = new QueryWrapper<>();
        wrapper.likeLeft("stockId", stockEnum.taiex.getStockCode());
        List<stock_detail> stocks_old = stockDetailMapper.selectList(wrapper);
        Set<String> stockId_old = stocks_old.stream().map(stock -> stock.getSId()).collect(Collectors.toSet());
        try {
            for (String param : parameters) {
                ArrayList<stock_detail> stocks = new ArrayList<>();
                String source = "";
                System.out.println(url + param);
                source = get_1.doGet(url + param);
                Thread.sleep(3500);
                stocks = parse_2.parse(source);
                for (stock_detail stock : stocks) {
                    if(!stockId_old.contains(stock.getSId())){
                        System.out.println(stock);
                        stockDetailMapper.insert(stock);
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





