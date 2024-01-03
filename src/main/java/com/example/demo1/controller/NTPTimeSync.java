package com.example.demo1.controller;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NTPTimeSync {
    public static void main(String[] args) {
//        System.out.println(timeSync());
    }

    private static void typeString(Robot robot, String s) {
        for (char c : s.toCharArray()) {
            int code = (int) c;

            if (Character.isLowerCase(c)) {
                // Nếu là chữ cái viết thường, thì chuyển thành chữ cái viết hoa
                code = Character.toUpperCase(c);
            } else if (c == ':') {
                // Nếu là dấu ":", thì thay thế bằng hai sự kiện nhấn phím ";" và nhấn phím Shift
                robot.delay(150);

                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                continue; // Để tránh nhập dấu ":" hai lần
            }

            // Nhấn phím tương ứng với ký tự c
            robot.delay(150);
            robot.keyPress(code);
            robot.keyRelease(code);
        }
    }

    private static void typeKey(Robot robot, int keyCode) {
        robot.delay(100);
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    public String timeSync() {
        String dateStr = ""; // Khai báo biến dateStr
        String timeStr = ""; // Khai báo biến timeStr

        try {
            // Synchronize time
            NTPUDPClient client = new NTPUDPClient();
            client.open();
            InetAddress address = InetAddress.getByName("pool.ntp.org");
            TimeInfo timeInfo = client.getTime(address);
            long serverTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            long localTime = System.currentTimeMillis();
            long synchronizedTime = localTime + (serverTime - timeInfo.getReturnTime()) + 7300L;
            Date realTime = new Date(synchronizedTime);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            dateStr = dateFormat.format(realTime);
            timeStr = timeFormat.format(realTime);

            System.out.println("Synchronized time: " + synchronizedTime);
            System.out.println("Date: " + dateStr);
            System.out.println("Time: " + timeStr);

            client.close();
            // Execute CMD commands
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.delay(250);
            typeString(robot, "cmd");
            robot.delay(250);


            // Nhấn đồng thời Ctrl + Shift + Enter
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_ENTER);

            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(700);

            robot.keyPress(KeyEvent.VK_ENTER);


            //  robot.delay(500);
            //  typeString(robot, "cd \\Windows\\System32 ");
            typeKey(robot, KeyEvent.VK_ENTER);
            robot.delay(150);
            typeString(robot, "date");
            typeKey(robot, KeyEvent.VK_ENTER);
            robot.delay(150);
            typeString(robot, dateStr); // Truyền giá trị của dateStr
            typeKey(robot, KeyEvent.VK_ENTER);
            robot.delay(150);
            typeString(robot, "time");
            typeKey(robot, KeyEvent.VK_ENTER);
            robot.delay(150);
            typeString(robot, timeStr); // Truyền giá trị của timeStr
            typeKey(robot, KeyEvent.VK_ENTER);
            robot.delay(150);
            // Thêm lệnh exit vào cuối để đóng cửa sổ Command Prompt
            typeString(robot, "exit");
            typeKey(robot, KeyEvent.VK_ENTER);
            return dateStr + " " + timeStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public long getTimeFromNTP(){
        String ntpServer = "pool.ntp.org"; // Địa chỉ máy chủ NTP

        try {
            NTPUDPClient client = new NTPUDPClient();
            client.open();

            InetAddress inetAddress = InetAddress.getByName(ntpServer);
            TimeInfo timeInfo = client.getTime(inetAddress);
            timeInfo.computeDetails(); // Tính toán thông tin thời gian

            long ntpTime = timeInfo.getMessage().getTransmitTimeStamp().getTime(); // Lấy thời gian từ NTP

            Date date = new Date(ntpTime);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Định dạng giờ, phút, giây

            String formattedTime = sdf.format(date);
            System.out.println("Thời gian từ NTP: " + formattedTime);
            return ntpTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String timeDifference() {
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress;
        try {
            long returnTime = getTimeFromNTP();
            long systemTime = System.currentTimeMillis();
            long offset = returnTime - systemTime;

            Date realTime = new Date(systemTime); // Chuyển đổi thành đối tượng Date
            Date ntpTime = new Date(returnTime); // Chuyển đổi thành đối tượng Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println("Local Time: " + dateFormat.format(realTime));
            System.out.println("NTP Time: " + dateFormat.format(ntpTime));

            System.out.println("Local Time: " + systemTime);
            System.out.println("NTP Time: " + returnTime);
            System.out.println("Time Difference: " + offset + " milliseconds");
            return "Time difference: " + offset + " milliseconds\n";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            timeClient.close();
        }
        return "Error.\n";
    }
}
