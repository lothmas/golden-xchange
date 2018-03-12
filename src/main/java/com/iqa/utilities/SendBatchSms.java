////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.iqa.utilities;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.stream.Stream;
//
//public class SendBatchSms {
//    public SendBatchSms() {
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        SendSms send = new SendSms();
//        String contents = new String(Files.readAllBytes(Paths.get("/home/f4939948/Documents/tsepo.txt", new String[0])));
//        String[] phoneNumbers = contents.split(",");
//        String[] var4 = phoneNumbers;
//        int var5 = phoneNumbers.length;
//
//        for(int var6 = 0; var6 < var5; ++var6) {
//            String phone = var4[var6];
//            System.out.println(phone.replace(" ", "").trim());
//            send.send("sendSms.sh", phone.replace(" ", "").trim(), "You are kindly reminded of a New community!!! www.goldenriches.co.za. No central account, 30% interest paid within a minimum of 3 days. Please visit the above website. The first list will be uploaded on the 16th of February 2018 at 06:00am. Minimum to assist is R300 maximum is R6500. Dont be left out.");
//        }
//
//    }
//
//    public void test() {
//        String fileName = "/home/GRusers.txt";
//
//        try {
//            Stream<String> stream = Files.lines(Paths.get(fileName, new String[0]));
//            Throwable var3 = null;
//
//            try {
//                stream.forEach(this::updateAdmin);
//            } catch (Throwable var13) {
//                var3 = var13;
//                throw var13;
//            } finally {
//                if(stream != null) {
//                    if(var3 != null) {
//                        try {
//                            stream.close();
//                        } catch (Throwable var12) {
//                            var3.addSuppressed(var12);
//                        }
//                    } else {
//                        stream.close();
//                    }
//                }
//
//            }
//        } catch (IOException var15) {
//            var15.printStackTrace();
//        }
//
//    }
//
//    private void updateAdmin(String s) {
//        String[] adminUsers = s.split(",");
//        System.out.println(adminUsers[0]);
//        System.out.println(adminUsers[1]);
//    }
//}
//
