package ru.tinkoff.edu.java.bot.processor.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {
    private final Configuration templateResolver;

    @Override
    public SendMessage sendMessage(Update update, String text) {
        return new SendMessage(update.message().chat().id(), text);
    }

    @Override
    @SneakyThrows
    public SendMessage sendTemplateId(Long tgChatId, String templateName, Map<String, Object> root) {
        Template template = templateResolver.getTemplate(templateName);
        Writer result = new StringWriter();
        template.process(root, result);
        return new SendMessage(tgChatId, result.toString())
            .parseMode(ParseMode.HTML);
    }

    @Override
    @SneakyThrows
    public SendMessage sendTemplateUpdate(Update update, String templateName, Map<String, Object> root) {
        Template template = templateResolver.getTemplate(templateName);
        Writer result = new StringWriter();
        template.process(root, result);
        return new SendMessage(update.message().chat().id(), result.toString())
            .parseMode(ParseMode.HTML);
    }
}
