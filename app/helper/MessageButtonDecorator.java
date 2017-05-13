package helper;

import logic.JacobConst;
import models.telegramModels.SendMessageModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by anatoly on 13.05.17.
 */
public class MessageButtonDecorator {
    SendMessageModel sendMessageModel;

    private MessageButtonDecorator() {
    }

    public static MessageButtonDecorator typicalDecorator(SendMessageModel sendMessageModel) {
        MessageButtonDecorator mbd = new MessageButtonDecorator();
        mbd.sendMessageModel = sendMessageModel;
        return mbd;
    }

    public void addButtonsLine(String... title) {
        ArrayList<String> newLine = new ArrayList<>(title.length);
        newLine.addAll(Arrays.asList(title));
        sendMessageModel.reply_markup.addNewLine(newLine);
    }

    public void addButtonsLine(List<String> line) {
        sendMessageModel.reply_markup.addNewLine(line);
    }

    public void addMenu() {
        addButtonsLine(JacobConst.TELEGRAM_COMMANDS.menu);
    }
}
