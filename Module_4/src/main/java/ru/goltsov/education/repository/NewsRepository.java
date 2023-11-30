package ru.goltsov.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.goltsov.education.model.News;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

}
