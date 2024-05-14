package ru.goltsov.education.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.model.News;
import ru.goltsov.education.repository.NewsRepository;
import ru.goltsov.education.repository.specification.NewsSpecification;
import ru.goltsov.education.utils.BeenUtils;
import ru.goltsov.education.web.model.request.NewsDeleteRequest;
import ru.goltsov.education.web.model.request.NewsFilterRequest;
import ru.goltsov.education.web.model.request.UpsertNewsRequest;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    @Autowired // можно указать поле final
    private NewsRepository newsRepository;

    public News save(News news) {
        return newsRepository.save(news);
    }

    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }


    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Новость с ID {0} не найдена!", id)));
    }

    public void delete(News news) {
        newsRepository.delete(news);
    }

    public boolean validateChanging(String userName, long newsId) {
        Optional<News> newsOptional = newsRepository.findById(newsId);
        if (newsOptional.isPresent()) {
            return newsOptional.get().getUser().getUsername().equals(userName);
        } else {
            throw new EntityNotFoundException(MessageFormat.format("Новость с ID {0} не найдена!", newsId));
        }
    }

    public News update(News news) {
        News newsForUpdate = findById(news.getId());

        BeenUtils.copyNonNullProperties(news, newsForUpdate);

        return save(newsForUpdate);
    }

    public List<News> findAllWithFilter(NewsFilterRequest filterRequest) {
        return newsRepository.findAll(NewsSpecification.withFilter(filterRequest));
    }
}
