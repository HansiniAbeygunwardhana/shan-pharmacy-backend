package com.example.PharmacyWebApplication.PharmacyWebApplication.service;

import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.OrderDetailDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.OrderDetail;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SalesService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    public int getNumberOfSalesForDay(Date date) {
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);
        List<OrderDetail> salesForDay = orderDetailDao.findByOrderPlacedAtBetween(
                new Timestamp(startOfDay.getTime()),
                new Timestamp(endOfDay.getTime())
        );
        return salesForDay.size();
    }

    public double getTotalSalesValueForDay(Date date) {
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);
        List<OrderDetail> salesForDay = orderDetailDao.findByOrderPlacedAtBetween(
                new Timestamp(startOfDay.getTime()),
                new Timestamp(endOfDay.getTime())
        );

        double totalSalesValue = 0;
        for (OrderDetail sale : salesForDay) {
            totalSalesValue += sale.getOrderAmount();
        }

        return totalSalesValue;
    }

    public int getNumberOfSalesForLast7Days() {
        Date sevenDaysAgo = getDaysAgoDate(7);
        List<OrderDetail> salesForLast7Days = orderDetailDao.findByOrderPlacedAtAfter(new Timestamp(sevenDaysAgo.getTime()));
        return salesForLast7Days.size();
    }

    public double getTotalSalesValueForLast7Days() {
        Date sevenDaysAgo = getDaysAgoDate(7);
        List<OrderDetail> salesForLast7Days = orderDetailDao.findByOrderPlacedAtAfter(new Timestamp(sevenDaysAgo.getTime()));

        double totalSalesValue = 0;
        for (OrderDetail sale : salesForLast7Days) {
            totalSalesValue += sale.getOrderAmount();
        }

        return totalSalesValue;
    }

    public int getNumberOfSalesForLast30Days() {
        Date thirtyDaysAgo = getDaysAgoDate(30);
        List<OrderDetail> salesForLast30Days = orderDetailDao.findByOrderPlacedAtAfter(new Timestamp(thirtyDaysAgo.getTime()));
        return salesForLast30Days.size();
    }

    public double getTotalSalesValueForLast30Days() {
        Date thirtyDaysAgo = getDaysAgoDate(30);
        List<OrderDetail> salesForLast30Days = orderDetailDao.findByOrderPlacedAtAfter(new Timestamp(thirtyDaysAgo.getTime()));

        double totalSalesValue = 0;
        for (OrderDetail sale : salesForLast30Days) {
            totalSalesValue += sale.getOrderAmount();
        }

        return totalSalesValue;
    }

    public Map<String, Double> getSalesValueByDayOfWeek() {
        Map<String, Double> salesValueByDayOfWeek = new HashMap<>();

        // Get the current date to calculate the start and end date of today
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Get sales for today
        String today = new SimpleDateFormat("EEEE").format(calendar.getTime());
        double totalSalesValueToday = getTotalSalesValueForDay(calendar.getTime());
        salesValueByDayOfWeek.put(today, totalSalesValueToday);

        // Go back 1 day and get sales for yesterday
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String yesterday = new SimpleDateFormat("EEEE").format(calendar.getTime());
        double totalSalesValueYesterday = getTotalSalesValueForDay(calendar.getTime());
        salesValueByDayOfWeek.put(yesterday, totalSalesValueYesterday);

        // Go back 1 day and get sales for 2 days ago
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String twoDaysAgo = new SimpleDateFormat("EEEE").format(calendar.getTime());
        double totalSalesValueTwoDaysAgo = getTotalSalesValueForDay(calendar.getTime());
        salesValueByDayOfWeek.put(twoDaysAgo, totalSalesValueTwoDaysAgo);

        // Go back 1 day and get sales for 3 days ago
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String threeDaysAgo = new SimpleDateFormat("EEEE").format(calendar.getTime());
        double totalSalesValueThreeDaysAgo = getTotalSalesValueForDay(calendar.getTime());
        salesValueByDayOfWeek.put(threeDaysAgo, totalSalesValueThreeDaysAgo);

        // Go back 1 day and get sales for 4 days ago
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String fourDaysAgo = new SimpleDateFormat("EEEE").format(calendar.getTime());
        double totalSalesValueFourDaysAgo = getTotalSalesValueForDay(calendar.getTime());
        salesValueByDayOfWeek.put(fourDaysAgo, totalSalesValueFourDaysAgo);

        // Go back 1 day and get sales for 5 days ago
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String fiveDaysAgo = new SimpleDateFormat("EEEE").format(calendar.getTime());
        double totalSalesValueFiveDaysAgo = getTotalSalesValueForDay(calendar.getTime());
        salesValueByDayOfWeek.put(fiveDaysAgo, totalSalesValueFiveDaysAgo);

        // Go back 1 day and get sales for 6 days ago
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String sixDaysAgo = new SimpleDateFormat("EEEE").format(calendar.getTime());
        double totalSalesValueSixDaysAgo = getTotalSalesValueForDay(calendar.getTime());
        salesValueByDayOfWeek.put(sixDaysAgo, totalSalesValueSixDaysAgo);

        return salesValueByDayOfWeek;
    }

    public Map<String, String> getPercentageOfSalesForEachProduct() {
        List<OrderDetail> allSales = (List<OrderDetail>) orderDetailDao.findAll();

        // Calculate the total sales value for all products
        Map<Product, Double> productSalesValueMap = new HashMap<>();
        for (OrderDetail sale : allSales) {
            Product product = sale.getProduct();
            double salesValue = productSalesValueMap.getOrDefault(product, 0.0);
            salesValue += sale.getOrderAmount();
            productSalesValueMap.put(product, salesValue);
        }

        // Calculate the total sales value across all products
        double totalSalesValue = 0;
        for (double salesValue : productSalesValueMap.values()) {
            totalSalesValue += salesValue;
        }

        // Calculate the percentage of sales for each product and format it with two decimal points
        Map<String, String> productSalesPercentageMap = new HashMap<>();
        for (Map.Entry<Product, Double> entry : productSalesValueMap.entrySet()) {
            Product product = entry.getKey();
            double salesValue = entry.getValue();
            double percentage = (salesValue / totalSalesValue) * 100;
            String formattedPercentage = String.format("%.2f", percentage);
            productSalesPercentageMap.put(product.getProductName(), formattedPercentage + "%");
        }

        return productSalesPercentageMap;
    }



    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private Date getDaysAgoDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTime();
    }
}
