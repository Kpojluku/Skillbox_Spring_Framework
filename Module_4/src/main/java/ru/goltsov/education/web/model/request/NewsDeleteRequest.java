package ru.goltsov.education.web.model.request;

import lombok.Data;

@Data
public class NewsDeleteRequest extends NewsChangeRequest {

    private long news_id;

}
