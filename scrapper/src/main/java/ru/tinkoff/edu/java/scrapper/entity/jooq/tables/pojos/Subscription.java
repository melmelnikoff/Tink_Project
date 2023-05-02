/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.entity.jooq.tables.pojos;


import jakarta.validation.constraints.NotNull;

import javax.annotation.processing.Generated;
import java.beans.ConstructorProperties;
import java.io.Serializable;


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
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tgChatId;
    private Long linkId;

    public Subscription() {
    }

    public Subscription(Subscription value) {
        this.tgChatId = value.tgChatId;
        this.linkId = value.linkId;
    }

    @ConstructorProperties({"tgChatId", "linkId"})
    public Subscription(
            @NotNull Long tgChatId,
            @NotNull Long linkId
    ) {
        this.tgChatId = tgChatId;
        this.linkId = linkId;
    }

    /**
     * Getter for <code>SUBSCRIPTION.TG_CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTgChatId() {
        return this.tgChatId;
    }

    /**
     * Setter for <code>SUBSCRIPTION.TG_CHAT_ID</code>.
     */
    public void setTgChatId(@NotNull Long tgChatId) {
        this.tgChatId = tgChatId;
    }

    /**
     * Getter for <code>SUBSCRIPTION.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>SUBSCRIPTION.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long linkId) {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Subscription other = (Subscription) obj;
        if (this.tgChatId == null) {
            if (other.tgChatId != null)
                return false;
        } else if (!this.tgChatId.equals(other.tgChatId))
            return false;
        if (this.linkId == null) {
            if (other.linkId != null)
                return false;
        } else if (!this.linkId.equals(other.linkId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.tgChatId == null) ? 0 : this.tgChatId.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Subscription (");

        sb.append(tgChatId);
        sb.append(", ").append(linkId);

        sb.append(")");
        return sb.toString();
    }
}
