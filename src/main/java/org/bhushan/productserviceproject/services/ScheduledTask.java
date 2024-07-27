package org.bhushan.productserviceproject.services;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@EnableScheduling
public class ScheduledTask {

//    @Scheduled(cron = "* * * * * *")
    void sendEmail() {
        System.out.println("Sending email");
        printLogTime("Sending email");
    }

    public static void printLogTime(String message) {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedTime = dateFormat.format(currentTime);
        System.out.println(formattedTime + " - " + message);
    }




}
