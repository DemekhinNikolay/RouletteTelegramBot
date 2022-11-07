package com.demekhin;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "ConsolBot";
    }

    @Override
    public String getBotToken() {
        return "5349494181:AAG";
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> startCommandReceived(chatId);

                case "/stop" -> stopCommandReceived(chatId, update.getMessage().getChat().getFirstName());

                default -> sendMessage(chatId, "Sorry, there is no such team");
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();



            if (callbackData.equals("betBlack")) {
                color = "black";
                String text = "Ваш выбор Black";
                executeEditMessageText(text, chatId, messageId);
                amountSelectionKeyboard(chatId);

            } else if (callbackData.equals("betRed")) {
                color = "red";
                String text = "Ваш выбор Red";
                executeEditMessageText(text, chatId, messageId);
                amountSelectionKeyboard(chatId);
            }


            else if (callbackData.equals("bet5")) {
                betAmount = 5;
                betProcessingService();
                String text = "Ваша сумма ставки " + betAmount + " $" + " Вам выпало " + resultRandomColor + " Это: " + victoryOrDefeat +
                        " Ваш баланс " + balance;
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals("bet10")) {
                betAmount = 10;
                betProcessingService();
                String text = "Ваша сумма ставки " + betAmount + " $" + " Вам выпало " + resultRandomColor + " Это: " + victoryOrDefeat +
                        " Ваш баланс " + balance;
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals("bet25")) {
                betAmount = 25;
                betProcessingService();
                String text = "Ваша сумма ставки " + betAmount + " $" + " Вам выпало " + resultRandomColor + " Это: " + victoryOrDefeat +
                        " Ваш баланс " + balance;
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals("bet50")) {
                betAmount = 50;
                betProcessingService();
                String text = "Ваша сумма ставки " + betAmount + " $" + " Вам выпало " + resultRandomColor + " Это: " + victoryOrDefeat +
                        " Ваш баланс " + balance;
                executeEditMessageText(text, chatId, messageId);
            }


        }

    }


    private String color;

    private int balance = 1000;
    private int betAmount;
    private String victoryOrDefeat;
    private int resultRandomNumber;
    private String resultRandomColor;


    private void startCommandReceived(long chatId) {
        betSelectionKeyboard(chatId);
    }

    private void stopCommandReceived(long chatId, String name) {

        String answer = "Good bye " + name + " com back to us";

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void betSelectionKeyboard(long chatId) {
        Server rouletteServer = new Server();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Hi, choose the type of bet\n" +
                "1 - black\n" +
                "2 - red\n" +
                "3 - Number\n" +
                "4 - Even\n" +
                "5 - Odd\n" +
                "6 - From 1 to 18\n" +
                "7 - From 19 to 36");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        rouletteServer.addButton(rowInLine, "1", "betBlack");
        rouletteServer.addButton(rowInLine, "2", "betRed");
        rouletteServer.addButton(rowInLine, "3", "betNumber");
        rouletteServer.addButton(rowInLine, "4", "betEven");
        rouletteServer.addButton(rowInLine, "5", "betOdd");
        rouletteServer.addButton(rowInLine, "6", "bet1to18");
        rouletteServer.addButton(rowInLine, "7", "bet19to36");

        rowsInLine.add(rowInLine);
        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void amountSelectionKeyboard(long chatId) {
        Server rouletteServer = new Server();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите сумму ставки, ваш банк $ " + balance);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        rouletteServer.addButton(rowInLine, "5", "bet5");
        rouletteServer.addButton(rowInLine, "10", "bet10");
        rouletteServer.addButton(rowInLine, "25", "bet25");
        rouletteServer.addButton(rowInLine, "50", "bet50");

        rowsInLine.add(rowInLine);
        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private void resultRandomNumber() {
        final List<String> colorArray = IntStream.range(0, 37).boxed()
                .map(x -> {
                    if (x == 0) {
                        return x + " green";
                    }
                    if (x % 2 == 0) {
                        return x + " red";
                    } else {
                        return x + " black";
                    }
                }).toList();

        resultRandomNumber = (int) Math.floor(Math.random() * colorArray.size());
        resultRandomColor = colorArray.get(resultRandomNumber);

    }


    private void betProcessingService() {
        resultRandomNumber();
        if (color.equals("black")) {
            if ((resultRandomNumber & 1) == 0) {
                balance -= betAmount;
                victoryOrDefeat = "loss";
            } else {
                balance += betAmount * 2;
                victoryOrDefeat = "win";
            }
        }else if (color.equals("red")) {
            if ((resultRandomNumber & 1) == 0) {
                balance += betAmount * 2;
                victoryOrDefeat = "win";
            } else {
                balance -= betAmount;
                victoryOrDefeat = "los";
            }
        }
    }

}
