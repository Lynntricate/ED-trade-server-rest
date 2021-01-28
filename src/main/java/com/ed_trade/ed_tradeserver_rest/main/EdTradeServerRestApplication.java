package com.ed_trade.ed_tradeserver_rest.main;

import com.ed_trade.ed_tradeserver_rest.logic.SystemComparison;
import com.ed_trade.ed_tradeserver_rest.beans.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class EdTradeServerRestApplication implements CommandLineRunner {
    private String sql = "USE EDDB\n" +
            "\n" +
            "IF OBJECT_ID('EDDB.dbo.modules') IS NOT NULL\n" +
            "    DROP TABLE EDDB.dbo.modules\n" +
            "\n" +
            "CREATE TABLE EDDB.dbo.modules\n" +
            "(\n" +
            "[id] INT NOT NULL,\n" +
            "[group_id] INT NOT NULL,\n" +
            "[class] INT NOT NULL,\n" +
            "[price] FLOAT,\n" +
            "[name] VARCHAR(100),\n" +
            "[ed_id] INT,\n" +
            "[ed_symbol] VARCHAR(100)\n" +
            ")\n" +
            "\n" +
            "Declare @JSON varchar(max)\n" +
            "\n" +
            "SELECT @JSON = BulkColumn\n" +
            "FROM OPENROWSET (BULK 'C:\\Users\\Jeroe\\IdeaProjects\\ED-trade-server-rest\\data\\json\\modules.json', SINGLE_CLOB) as j\n" +
            "\n" +
            "INSERT INTO EDDB.dbo.modules\n" +
            "(\n" +
            "[id],\n" +
            "[group_id],\n" +
            "[class],\n" +
            "[price],\n" +
            "[name],\n" +
            "[ed_id],\n" +
            "[ed_symbol]\n" +
            ")\n" +
            "SELECT * FROM OPENJSON (@JSON)\n" +
            "WITH(\n" +
            "[id] int,\n" +
            "[group_id] int,\n" +
            "[class] int,\n" +
            "[price] float,\n" +
            "[name] VARCHAR(100),\n" +
            "[ed_id] int,\n" +
            "[ed_symbol] VARCHAR(100)\n" +
            ") as Modules";


    @Autowired
    private JdbcTemplate jdbcTemp;

    public static void main(String[] args) {
        SpringApplication.run(EdTradeServerRestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Setup.setupAll(false);
        System.out.println(SystemComparison.getLineDistanceSystems("Borr", "LHS 1240"));

        jdbcTemp.execute(sql);



//        String sql = "SELECT * FROM EDDB.dbo.listings AS l WHERE l.station_id=4";
//        List<Listing> listings = jdbcTemp.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
//
//        for (Listing l : listings) {
//            System.out.println(l.getCommodity_id());
//        }
    }


}
