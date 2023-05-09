package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.processor.commands.GetListLinksCommand;
import ru.tinkoff.edu.java.bot.processor.message.MessageSenderImpl;
import ru.tinkoff.edu.java.bot.service.LinkServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetListOfLinksCommandTest {

    @InjectMocks
    GetListLinksCommand getListLinksCommand;
    @Mock
    private MessageSenderImpl messageSender;
    @Mock
    private LinkServiceImpl linkService;

    @Test
    void process__callLinkServiceAndMessageSender_expectOneInvocation() {
        // given
        long tgChatId = 123;
        List<LinkResponse> links = List.of(new LinkResponse(1L, URI.create("github.com")));
        var update = getUpdate(tgChatId);

        when(linkService.getAllLinks(tgChatId)).thenReturn(links);

        // when
        getListLinksCommand.process(update);

        // then
        verify(linkService, times(1)).getAllLinks(tgChatId);
        verify(messageSender, times(1))
            .sendTemplateUpdate(update, "links.ftl", Map.of("links", links));

    }

    @Test
    void process__processGetAllLinksWithNoLinks_returnSpecialMessage() {
        // given
        long tgChatId = 123;
        List<LinkResponse> links = List.of();
        var update = getUpdate(tgChatId);
        SendMessage specialMessage = new SendMessage(update, "Special message");

        when(getListLinksCommand.process(update)).thenReturn(specialMessage);

        //when
        var result = getListLinksCommand.process(update);

        //then
        assertEquals(result, specialMessage, "Not equals");

    }

    Update getUpdate(Long tgChatId) {
        //Update -> Message -> Chat -> Id
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        ReflectionTestUtils.setField(chat, "id", 123L);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(update, "message", message);
        return update;
    }

}
