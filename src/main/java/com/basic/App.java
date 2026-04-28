package com.basic;

import java.io.IOException;
import java.util.Scanner;

import com.basic.http.ApiServer;

public class App {

  public static void main(String[] args) throws IOException {
    ApiServer server = new ApiServer();
    Scanner sc = new Scanner(System.in);
    server.start();

    do {
      System.out.println("Digite STOP para desligar o servidor. ");
    } while (!sc.next().equalsIgnoreCase("Stop"));
    server.stop();
    sc.close();
    System.out.println("Servidor desligado. ");
  }
}
