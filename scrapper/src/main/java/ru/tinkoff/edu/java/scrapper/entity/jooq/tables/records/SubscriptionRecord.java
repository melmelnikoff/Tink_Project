/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records;


import jakarta.validation.constraints.NotNull;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Subscription;

import javax.annotation.processing.Generated;
import java.beans.ConstructorProperties;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "https://www.jooq.org",
                "jOOQ version:3.17.6"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class SubscriptionRecord extends UpdatableRecordImpl<SubscriptionRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>SUBSCRIPTION.TG_CHAT_ID</code>.
     */
    public void setTgChatId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>SUBSCRIPTION.TG_CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTgChatId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>SUBSCRIPTION.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>SUBSCRIPTION.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Subscription.SUBSCRIPTION.TG_CHAT_ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return Subscription.SUBSCRIPTION.LINK_ID;
    }

    @Override
    @NotNull
    public Long component1() {
        return getTgChatId();
    }

    @Override
    @NotNull
    public Long component2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public Long value1() {
        return getTgChatId();
    }

    @Override
    @NotNull
    public Long value2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public SubscriptionRecord value1(@NotNull Long value) {
        setTgChatId(value);
        return this;
    }

    @Override
    @NotNull
    public SubscriptionRecord value2(@NotNull Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public SubscriptionRecord values(@NotNull Long value1, @NotNull Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SubscriptionRecord
     */
    public SubscriptionRecord() {
        super(Subscription.SUBSCRIPTION);
    }

    /**
     * Create a detached, initialised SubscriptionRecord
     */
    @ConstructorProperties({"tgChatId", "linkId"})
    public SubscriptionRecord(@NotNull Long tgChatId, @NotNull Long linkId) {
        super(Subscription.SUBSCRIPTION);

        setTgChatId(tgChatId);
        setLinkId(linkId);
    }

    /**
     * Create a detached, initialised SubscriptionRecord
     */
    public SubscriptionRecord(ru.tinkoff.edu.java.scrapper.entity.jooq.tables.pojos.Subscription value) {
        super(Subscription.SUBSCRIPTION);

        if (value != null) {
            setTgChatId(value.getTgChatId());
            setLinkId(value.getLinkId());
        }
    }
}
