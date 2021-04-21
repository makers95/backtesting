package com.stream.crawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stream.crawler.mapper.StockDetailMapper;
import com.stream.crawler.model.stockEnum;
import com.stream.crawler.model.stock_detail;
import com.stream.crawler.parse.parse_2;
import com.stream.crawler.read.get_1;
import com.stream.crawler.service.crawlerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Param $
 * @return $
 **/
@Service
public class crawlerServiceImpl implements crawlerService {

    @Resource
    StockDetailMapper stockDetailMapper;

    @Override
    public void twseMonth(String yyyyMM01) {

        if(null ==yyyyMM01){
            LocalDate now = LocalDate.now();
            yyyyMM01 = ""+now.getYear()+now.getMonth()+"01";
        }

        String url = "https://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date="+yyyyMM01 ;
        try {
            String source = get_1.doGet(url);

            //check repeat
            QueryWrapper<stock_detail> wrapper = new QueryWrapper<>();
            wrapper.likeRight("stockId",yyyyMM01.substring(0,6));
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
}