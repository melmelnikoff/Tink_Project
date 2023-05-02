package ru.tinkoff.edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
//TODO: посмотреть на pushedAt

public record GitHubApiResponse(
        @JsonProperty("id") long id,
        @JsonProperty("name") String repositoryName,
        @JsonProperty("clone_url") String cloneUrl,
        @JsonProperty("created_at") OffsetDateTime createdAt,
        @JsonProperty("updated_at") OffsetDateTime updatedAt,
        @JsonProperty("pushed_at") OffsetDateTime pushedAt,
        @JsonProperty("open_issues_count") Integer openIssuesCount

) {
}

