package com.demekhin;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.IntStream;

public class Server {









    public void addButton(List<InlineKeyboardButton> buttonList, String setText , String callback) {

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(setText);
        button.setCallbackData(callback);
        buttonList.add(button);
    }

}
