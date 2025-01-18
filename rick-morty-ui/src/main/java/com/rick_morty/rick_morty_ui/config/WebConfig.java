package com.rick_morty.rick_morty_ui.config;

import com.rick_morty.rick_morty_ui.service.utils.LocationSummaryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LocationSummaryConverter locationSummaryConverter;
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(locationSummaryConverter);
    }
}
