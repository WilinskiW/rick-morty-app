package com.rick_morty.rick_morty_web_api.scheduling;

import com.rick_morty.rick_morty_web_api.api.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulingTasks {
    private final CharacterService characterService;

    @Scheduled(fixedRate = 300000) //5 m
    @Async
    public void performTask(){
        try {
            characterService.setScheduleCharacter();
        }
        catch (IllegalArgumentException ignored){}
    }
}
