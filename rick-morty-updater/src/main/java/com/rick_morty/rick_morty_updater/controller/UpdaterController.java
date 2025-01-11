package com.rick_morty.rick_morty_updater.controller;

import com.rick_morty.rick_morty_updater.updater.RickAndMortyDataUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/updater")
public class UpdaterController {
    private final RickAndMortyDataUpdater dbUpdater;

    public UpdaterController(RickAndMortyDataUpdater dbUpdater) {
        this.dbUpdater = dbUpdater;
    }

    @RequestMapping("/start")
    public ResponseEntity<String> startUpdate(){
        dbUpdater.syncData();
        return ResponseEntity.ok("Updater started..." + LocalDateTime.now());
    }
}
