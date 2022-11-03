package com.demekhin;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.IntStream;

public class Server {







    private final List<String> colorArray = IntStream.range(0, 37).boxed()
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

    public void addButton(List<InlineKeyboardButton> buttonList, String setText , String callback) {

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(setText);
        button.setCallbackData(callback);
        buttonList.add(button);
    }

}
