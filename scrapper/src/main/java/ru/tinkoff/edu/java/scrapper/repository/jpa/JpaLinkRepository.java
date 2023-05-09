package ru.tinkoff.edu.java.scrapper.repository.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.entity.Link;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByUrl(String url);

    @Modifying
    @Query("update Link l set l.lastCheckTime = :lastCheckTime, l.updatedAt = :updatedAt,\n"
        + "l.updatesCount = :updatesCount where l.id = :id")
    void update(
        OffsetDateTime lastCheckTime,
        OffsetDateTime updatedAt,
        Integer updatesCount,
        Long id
    );

    @Query(value = "select * from link order by last_check_time nulls first limit :limit", nativeQuery = true)
    List<Link> findCheckedLongTimeAgoLinks(@Param("limit") int limit);

}
