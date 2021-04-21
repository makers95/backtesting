package com.stream.backtesting.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Description 簡易股市資料
 * @Param $
 * @return $
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class stock {
    @TableId(value = "stockId",type = IdType.ASSIGN_ID)
    private String sId;

    private String name;
    private Float price;
    private LocalDate date;
}
