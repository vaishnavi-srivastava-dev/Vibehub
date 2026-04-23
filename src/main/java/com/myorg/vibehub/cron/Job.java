package com.myorg.vibehub.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//cron job symbols meaning-
// - : range
// , : multiple values for one unit
// ? : no special value
// / : interval
// * : every  value
@Component
public class Job {
    //task every minute
//    @Scheduled(cron="0 * * * * ?")
//    public void task(){
//        System.out.println("task running");
//    }

    //Task twice every one minute
//    @Scheduled(cron = "*/30 * * * * ?")
//    public void task(){
//        System.out.println("task running");
//    }

    //task running every 5 secs
//    @Scheduled(cron = "*/5 * * * * ?")
//    public void task(){
//        System.out.println("task running");
//    }

    //task running 5 times every minute
//    @Scheduled(cron = "*/12 * * * * ?")
//    public void task(){
//        System.out.println("task running");
//    }

    //task running every 5 min and between 12 AM To 7:59 AM and 5PM to 12AM
    @Scheduled(cron = "0 */5 0-7,17-23 * * SAT,SUN" )
    public void task(){
        System.out.println("task is running");
    }

    // Except 1st Jan
//    @Scheduled(cron = "0 0 1 2-31 JAN ?")
//    @Scheduled(cron = "0 0 1 * FEB-DEC ?")
//    public void printHello() {
//        System.out.println("Hello");
}
