package com.stream.backtesting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stream.backtesting.model.stock;
import com.stream.backtesting.model.stock_detail;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Param $
 * @return $
 **/
@Repository
public interface StockMapper extends BaseMapper<stock> {
}
