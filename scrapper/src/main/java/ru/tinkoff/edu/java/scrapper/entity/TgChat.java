package ru.tinkoff.edu.java.scrapper.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "tg_chat")
@Accessors(chain = true)
public class TgChat {

    @Id
    @Column(name = "id")
    private long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "subscription",
               joinColumns = @JoinColumn(name = "tg_chat_id"),
               inverseJoinColumns = @JoinColumn(name = "link_id"))

    private Set<Link> links = new HashSet<>();

}
