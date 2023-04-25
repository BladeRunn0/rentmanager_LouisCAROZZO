package com.epf.rentmanager;


import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.epf.rentmanager.service", "com.epf.rentmanager.dao", "com.epf.rentmanager.persistence"})
public class AppConfiguration {


}
