package ru.tinkoff.edu.java.scrapper.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {
    private Long id;
    private URI url;
    private OffsetDateTime lastCheckTime;
    private OffsetDateTime updatedAt;
    private Integer updatesCount;
}
