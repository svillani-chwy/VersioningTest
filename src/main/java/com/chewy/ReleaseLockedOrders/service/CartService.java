package com.chewy.ReleaseLockedOrders.service;

import com.chewy.ReleaseLockedOrders.configuration.HikariDriverConfig;
import com.chewy.ReleaseLockedOrders.domain.Cart;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    private HikariDriverConfig hikariConfig;

    private final String MINS = "minutes";
    private final String LIMIT = "limit";

    private Logger logger;

    public CartService(Logger logger) throws Exception {
        hikariConfig = new HikariDriverConfig();
        this.logger = logger;
    }

    public List<Cart> getLockedCarts() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now(Clock.systemUTC()).minusMinutes((System.getenv().get(MINS) != null ? Integer.valueOf(System.getenv().get(MINS)) : 10));

        String limit = (System.getenv().get(LIMIT) != null ? " limit " + System.getenv().get(LIMIT) : "");

        String sqlQuery = "select id, account_id, editor_account_id from cart.cart where editor_account_id notnull and status = 0 and update_time > ?" + limit;

        List<Cart> carts = new ArrayList<>();

        try (Connection con = hikariConfig.getConnection()) {
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setTimestamp(1, Timestamp.valueOf(formatter.format(time)));
            logger.info(pst.toString());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                carts.add(new Cart(rs.getString("id"), rs.getString("account_id"), rs.getString("editor_account_id")));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return carts;
    }

}
