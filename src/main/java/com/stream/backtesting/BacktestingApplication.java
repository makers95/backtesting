package com.stream.backtesting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stream.backtesting.mapper")
public class BacktestingApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(BacktestingApplication.class, args);

    }

}
