package com.stream.crawler.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


/**
 * @Description
 * @Param $
 * @return $
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class stock_detail {
    @TableId(value = "stockId",type = IdType.ASSIGN_ID)
    private String sId;

    private String name;
    private Float price_open;
    private Float price_high;
    private Float price_low;
    private Float price_end;
    private LocalDate date;
}
