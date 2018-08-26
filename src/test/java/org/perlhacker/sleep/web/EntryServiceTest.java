package org.perlhacker.sleep.web;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class EntryServiceTest {

    private EntryService entryService;

    @Before
    public void setup() throws Exception {
        File tempFile = File.createTempFile("sleepweb", "txt");
        tempFile.deleteOnExit();

        entryService = new EntryService(new File(getClass().getResource("/schlaf.txt").toURI()), tempFile);
    }

    @Test
    public void readEntries() throws IOException {
        List<Entry> entries = entryService.getEntries();
        assertEquals(5, entries.size());

        Entry first = entries.get(0);
        assertEquals("2018-08-16", first.getDate().toString());
        assertEquals(6, first.getSleepQuality());
        assertEquals(6, first.getDayQuality());
        assertEquals("foo", first.getComment());
    }

    @Test
    public void formatLine() {
        String line = EntryService.toLine(new Entry(LocalDate.of(2018, 1, 1), 9, 10, "test"));
        assertEquals("2018-01-01  9 10 test", line);

        String line2 = EntryService.toLine(new Entry(LocalDate.of(2018, 1, 1), 10, 9, "test"));
        assertEquals("2018-01-01 10  9 test", line2);

        String line3 = EntryService.toLine(new Entry(LocalDate.of(2018, 1, 1), 10, 10, "test"));
        assertEquals("2018-01-01 10 10 test", line3);
    }

    @Test
    public void addsEntry() throws Exception {
        Entry entry = new Entry(LocalDate.of(2018, 1, 1), 10, 10, "test");

        entryService.addEntry(entry);

        List<String> lines = Files.lines(entryService.getOutputLocation().toPath()).collect(Collectors.toList());
        assertEquals(6, lines.size());
        assertEquals("2018-01-01 10 10 test", lines.get(lines.size() -1));
    }
}