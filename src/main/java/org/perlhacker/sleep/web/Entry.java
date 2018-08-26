package org.perlhacker.sleep.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public final class Entry {
    private final LocalDate date;
    private final int sleepQuality;
    private final int dayQuality;
    private final String comment;

    @JsonCreator
    public Entry(
            @JsonProperty("date") LocalDate date,
            @JsonProperty("sleepQuality") int sleepQuality,
            @JsonProperty("dayQuality") int dayQuality,
            @JsonProperty("comment") String comment
    ) {
        this.date = date;
        this.sleepQuality = sleepQuality;
        this.dayQuality = dayQuality;
        this.comment = comment;
    }

}