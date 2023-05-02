package ru.tinkoff.edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowItemApiResponse(
        @JsonProperty("question_id") long questionId,
        @JsonProperty("is_answered") boolean isAnswered,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
        @JsonProperty("creation_date") OffsetDateTime creationDate,
        @JsonProperty("last_edit_date") OffsetDateTime lastEditDate,
        @JsonProperty("link") String link,
        @JsonProperty("answer_count") Integer answerCount

) {
}
