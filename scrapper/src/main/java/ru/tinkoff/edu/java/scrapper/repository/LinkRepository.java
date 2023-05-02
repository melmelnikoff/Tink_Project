package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    Link save(Link link);

    void update(Link link);

    List<Link> findAll();

    Optional<Link> findById(Long id);

    Optional<Link> findLinkByUrl(String url);

    void deleteById(Long id);

    List<Link> findCheckedLongTimeAgoLinks(int limit);


}
