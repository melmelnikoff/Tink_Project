package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BotUpdatesDispatcherTest {

    @Mock
    private TelegramBot bot;

    @Mock
    private CommandProcessor commandProcessor;

    @InjectMocks
    BotUpdatesDispatcher botUpdatesDispatcher;

    @Captor
    ArgumentCaptor<SendMessage> sendMessageArgumentCaptor;


    @Test
    void process__unexpectedCommand_returnSpecialMessage() {
        // given
        String specialMessage = "I don't have this command. Use /help to see all my commands";
        Update update = getUpdate();
        when(commandProcessor.process(update)).thenReturn(Optional.empty());

        // when
        botUpdatesDispatcher.process(List.of(update));

        verify(bot, times(1)).execute(sendMessageArgumentCaptor.capture());

        //then
        assertEquals(sendMessageArgumentCaptor.getValue().getParameters().get("text"), specialMessage);



    }

    // TODO: more tests

    Update getUpdate(){
        //Update -> Message -> Chat -> Id
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        ReflectionTestUtils.setField(chat, "id", 123L);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/unexpected");
        ReflectionTestUtils.setField(update, "message", message);
        return update;
    }

}
