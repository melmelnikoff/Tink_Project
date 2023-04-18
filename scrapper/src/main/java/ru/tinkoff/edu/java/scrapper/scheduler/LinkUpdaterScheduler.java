package ru.tinkoff.edu.java.scrapper.scheduler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.updater.LinksUpdater;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinksUpdater linksUpdater;

    @Value("#{@schedulerLimitForUpdateLinks}")
    private Integer updateLinkLimit;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        linksUpdater.updateLinks(updateLinkLimit);
        log.info("Update method in LinkUpdaterScheduler class");

    }
}
