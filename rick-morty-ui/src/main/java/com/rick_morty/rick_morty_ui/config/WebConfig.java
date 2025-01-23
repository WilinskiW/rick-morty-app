package com.rick_morty.rick_morty_ui.config;

import com.rick_morty.rick_morty_ui.converters.CharacterConverter;
import com.rick_morty.rick_morty_ui.converters.LocationSummaryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LocationSummaryConverter locationSummaryConverter;
    private final CharacterConverter characterConverter;

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(locationSummaryConverter);
        registry.addConverter(characterConverter);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
