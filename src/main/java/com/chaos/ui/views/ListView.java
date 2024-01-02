package com.chaos.ui.views;

import com.chaos.ui.pojo.AzSQLConfiguration;
import com.chaos.ui.pojo.CustomerVO;
import com.chaos.ui.utils.AzSQLUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Demo App")
@EnableConfigurationProperties(AzSQLConfiguration.class)
public class ListView extends VerticalLayout {
    final Grid<CustomerVO> grid;
    private Button loadFromDB;
    private Button writeLogs;
    private Button uploadData;
    private Button load1ORecordsFromDB;

    @Autowired
    private AzSQLConfiguration properties;

    public ListView() {
        System.out.println("list view");
        loadFromDB = new Button("Refresh");
        writeLogs = new Button("Write Logs ");
        uploadData = new Button("Upload 1000 records ");
        load1ORecordsFromDB = new Button("Refresh 10 record ");
        this.grid = new Grid<>(CustomerVO.class);
        /*System.out.println("\n\n\nproperties "+properties.getHostname());
        try {
            long begin = System.currentTimeMillis();
            grid.setItems(AzSQLUtil.getInstance().readAllRecords(properties));
            long end = System.currentTimeMillis();
            Notification.show("Grid load time : "+(end-begin) +" milliseconds");
        } catch (SQLException e) {
            Notification.show("Error loading data from DB");
        }*/
        add(loadFromDB);
        add(load1ORecordsFromDB);
        add(writeLogs);
        add(uploadData);
        add(grid);
        loadFromDB.addClickListener(clickEvent -> {
            try {
                long begin = System.currentTimeMillis();
                grid.setItems(AzSQLUtil.getInstance().readAllRecords(properties));
                long end = System.currentTimeMillis();
                Notification.show("Grid load time : "+(end-begin) +" milliseconds");
            } catch (SQLException e) {
                Notification.show("Error loading data from DB");
            }
        });
        load1ORecordsFromDB.addClickListener(clickEvent -> {
            try {
                long begin = System.currentTimeMillis();
                grid.setItems(AzSQLUtil.getInstance().readAllRecordsTop10(properties));
                long end = System.currentTimeMillis();
                Notification.show("Grid load time : "+(end-begin) +" milliseconds");
            } catch (SQLException e) {
                Notification.show("Error loading data from DB");
            }
        });
        writeLogs.addClickListener(clickEvent -> {
            try {
                long begin = System.currentTimeMillis();
                Notification.show("Start logging the data ");
                List<CustomerVO> lists = AzSQLUtil.getInstance().readAllRecords(properties);
                //
                Notification.show("List Size "+lists.size());
                for(CustomerVO vo: lists) {
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter("/var/run/temp/logs", true));) {
                        writer.append(vo.toString());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                long end = System.currentTimeMillis();
                Notification.show("Logged data in : "+(end-begin) +" milliseconds");
            } catch (SQLException e) {
                Notification.show("Error loading data from DB");
            }
        });
        uploadData.addClickListener(clickEvent -> {
            try {
                long begin = System.currentTimeMillis();
                AzSQLUtil.getInstance().insertData(properties);
                long end = System.currentTimeMillis();
                Notification.show("Grid load time : "+(end-begin) +" milliseconds");
            } catch (SQLException e) {
                Notification.show("Error loading data from DB");
            }
        });
    }

}