package com.stream.backtesting.save;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stream.backtesting.mapper.StockMapper;
import com.stream.backtesting.model.stock;
import com.stream.backtesting.model.stockEnum;
import com.stream.crawler.read.get_1;
import com.stream.crawler.parse.parse_1;
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
public class twse_return {

    @Resource
    private StockMapper stockMapper;

    @Test
    public void Test() throws Exception {
        get_1 get_1 = new get_1();
        String url = "https://www.twse.com.tw/indicesReport/MFI94U?response=html&date=20210301";
        String source = get_1.doGet(url);
        System.out.println(source);

//        List<String> parameters = get_1.getParmaList();
//        parameters.forEach(System.out::println);
    }

    //股市總加權指數
    @Test
    public void crawler() throws Exception {
        get_1 get_1 = new get_1();
        parse_1 parse_1 = new parse_1();
        String url = "https://www.twse.com.tw/indicesReport/MFI94U?response=html&date=";
        //產生參數陣列
        List<String> parameters = get_1.getParmaList(2003);
        //check repeat
        QueryWrapper<stock> wrapper = new QueryWrapper<>();
        wrapper.likeLeft("stockId", stockEnum.taiex_total_return.getStockCode());
        List<stock> stocks_old = stockMapper.selectList(wrapper);

        Set<String> stockId_old = stocks_old.stream().map(stock -> stock.getSId()).collect(Collectors.toSet());

        for (String param : parameters) {
            ArrayList<stock> stocks = new ArrayList<>();
            String source = "";
            System.out.println(url + param);
            source = get_1.doGet(url + param);
            Thread.sleep(3500);
            stocks = parse_1.parseData_1(source);
            for (stock stock : stocks) {
                if(!stockId_old.contains(stock.getSId())){
                stockMapper.insert(stock);
                }

            }
        }
    }
}





