package org.perlhacker.sleep.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EntryController {
    private final EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @RequestMapping("/entries")
    public List<Entry> getEntries() throws IOException {
        return entryService.getEntries();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/entries")
    public ResponseEntity<Entry> addEntry(@RequestBody Entry entry) throws IOException {
        entryService.addEntry(entry);
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }
}