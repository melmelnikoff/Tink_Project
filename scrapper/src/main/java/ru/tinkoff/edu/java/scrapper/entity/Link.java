package ru.tinkoff.edu.java.scrapper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "link")
@Accessors(chain = true)
public class Link {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "last_check_time")
    private OffsetDateTime lastCheckTime;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "updates_count")
    private Integer updatesCount;

    @ManyToMany(cascade = CascadeType.REMOVE,
            mappedBy = "links")
    private Set<TgChat> tgChats = new HashSet<>();


}
