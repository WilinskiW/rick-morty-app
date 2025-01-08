package com.rick_morty.rick_morty_updater.controller;

import com.rick_morty.rick_morty_updater.updater.DbUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/updater")
public class UpdaterController {
    private final DbUpdater dbUpdater;

    public UpdaterController(DbUpdater dbUpdater) {
        this.dbUpdater = dbUpdater;
    }

    @RequestMapping("/start")
    public ResponseEntity<String> startUpdate(){
        dbUpdater.updateDatabase();
        return ResponseEntity.ok("Updater started...");
    }
}
