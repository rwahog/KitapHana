package com.kitaphana;

import com.kitaphana.Service.TelegramBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {
  public static void main(String[] args) {
    ApiContextInitializer.init();

    TelegramBotsApi botsApi = new TelegramBotsApi();
    TelegramBot ourCoolBot = new TelegramBot();
    try {
      botsApi.registerBot(ourCoolBot);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
    System.out.println("Bot started!");
  }
}
