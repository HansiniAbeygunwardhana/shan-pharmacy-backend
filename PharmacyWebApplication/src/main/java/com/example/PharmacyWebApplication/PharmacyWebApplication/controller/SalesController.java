package com.example.PharmacyWebApplication.PharmacyWebApplication.controller;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Product;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// SalesController.java
@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping("/day")
    public ResponseEntity<Map<String, Object>> getSalesForDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Map<String, Object> response = new HashMap<>();
        int numberOfSales = salesService.getNumberOfSalesForDay(date);
        double totalSalesValue = salesService.getTotalSalesValueForDay(date);

        response.put("numberOfSales", numberOfSales);
        response.put("totalSalesValue", totalSalesValue);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/last7days")
    public ResponseEntity<Map<String, Object>> getSalesForLast7Days() {
        Map<String, Object> response = new HashMap<>();
        int numberOfSales = salesService.getNumberOfSalesForLast7Days();
        double totalSalesValue = salesService.getTotalSalesValueForLast7Days();

        response.put("numberOfSales", numberOfSales);
        response.put("totalSalesValue", totalSalesValue);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/last30days")
    public ResponseEntity<Map<String, Object>> getSalesForLast30Days() {
        Map<String, Object> response = new HashMap<>();
        int numberOfSales = salesService.getNumberOfSalesForLast30Days();
        double totalSalesValue = salesService.getTotalSalesValueForLast30Days();

        response.put("numberOfSales", numberOfSales);
        response.put("totalSalesValue", totalSalesValue);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/bydayofweek")
    public ResponseEntity<Map<String, Double>> getSalesValueByDayOfWeek() {
        Map<String, Double> salesValueByDayOfWeek = salesService.getSalesValueByDayOfWeek();
        return ResponseEntity.ok(salesValueByDayOfWeek);
    }

    @GetMapping("/percentages")
    public ResponseEntity<Map<String, String>> getPercentageOfSalesForEachProduct() {
        Map<String, String> productSalesPercentageMap = salesService.getPercentageOfSalesForEachProduct();
        return ResponseEntity.ok(productSalesPercentageMap);
    }
}
