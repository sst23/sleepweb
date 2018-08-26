package org.perlhacker.sleep.web;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Getter
@Log
public final class EntryService {
    private static final Pattern ENTRY_PATTERN = Pattern.compile("^(\\d+)-(\\d+)-(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*(.*)$");

    private final File inputLocation;
    private final File outputLocation;

    @Autowired
    public EntryService(@Qualifier("input") File inputLocation, @Qualifier("output") File outputLocation) {
        this.inputLocation = inputLocation;
        this.outputLocation = outputLocation;
    }


    public List<Entry> getEntries() throws IOException {
        List<Entry> entries = new ArrayList<>();

        try (Stream<String> lines = Files.lines(inputLocation.toPath())) {
            lines.forEach(l -> entries.add(toEntry(l)));
        }

        return entries;
    }

    public void addEntry(@NonNull Entry entry) throws IOException {
        List<Entry> entries = getEntries();
        entries.add(entry);

        String out = entries.stream().map(EntryService::toLine).collect(Collectors.joining("\n"));

        try (PrintWriter writer = new PrintWriter(outputLocation)) {
            writer.println(out);
            log.info("Added new entry: " + entry);
        }
    }

    public static String toLine(Entry entry) {
        return String.format("%s %2s %2s %s", entry.getDate(), entry.getSleepQuality(), entry.getDayQuality(), entry.getComment());
    }

    private static Entry toEntry(String line) {
        Matcher matcher = ENTRY_PATTERN.matcher(line);

        if (matcher.find()) {
            int day = Integer.valueOf(matcher.group(3));
            int month = Integer.valueOf(matcher.group(2));
            int year = Integer.valueOf(matcher.group(1));

            int sleepQuality = Integer.valueOf(matcher.group(4));
            int dayQuality = Integer.valueOf(matcher.group(5));

            String comment = matcher.group(6);

            return new Entry(LocalDate.of(year, month, day), sleepQuality, dayQuality, comment);
        }

        throw new RuntimeException("invalid line: " + line);
    }
}
