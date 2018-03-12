////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.iqa.utilities;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class SendSms {
//    String path = "/home/sendSms/";
//
//    public SendSms() {
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//    }
//
//    public void send(String fileName, String phoneNumber, String message) throws IOException, InterruptedException {
//        try {
//            PrintWriter writer = new PrintWriter(this.path + fileName, "UTF-8");
//            writer.println("curl -i \\\n-X POST \\\n-H \"Content-Type: application/json\" \\\n-H \"Accept: application/json\" \\\n-H \"Authorization: bPgWm2TBQuO2VJG1dPoLCQ==\" \\");
//            writer.println("-d '{\"content\": \"" + message + "\", \"to\": [\"" + phoneNumber + "\"]}' \\");
//            writer.println("-s https://platform.clickatell.com/messages");
//            writer.close();
//        } catch (IOException var6) {
//            ;
//        }
//
//        Process p = Runtime.getRuntime().exec("chmod 777 " + this.path + fileName);
//        p.waitFor();
//        Process proc = Runtime.getRuntime().exec(this.path + fileName);
//        proc.waitFor();
//    }
//}
//
