package ru.tinkoff.edu.java.scrapper.repository.jooq;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.TG_CHAT;

@RequiredArgsConstructor
public class JooqTgChatRepository implements TgChatRepository {

    private final DSLContext create;

    @Override
    public void save(TgChat tgChat) {
        create.insertInto(TG_CHAT)
            .columns(TG_CHAT.ID, TG_CHAT.CREATED_AT)
            .values(tgChat.getId(), OffsetDateTime.now())
            .execute();
    }

    @Override
    public List<TgChat> findAll() {
        return create
            .select(TG_CHAT.ID, TG_CHAT.CREATED_AT)
            .from(TG_CHAT)
            .fetchInto(TgChat.class);
    }

    @Override
    public Optional<TgChat> findById(Long id) {
        return create
            .select(TG_CHAT.ID, TG_CHAT.CREATED_AT)
            .from(TG_CHAT)
            .where(TG_CHAT.ID.eq(id))
            .fetchOptionalInto(TgChat.class);
    }

    @Override
    public void deleteById(Long id) {
        create.delete(TG_CHAT)
            .where(TG_CHAT.ID.eq(id))
            .execute();

    }
}
