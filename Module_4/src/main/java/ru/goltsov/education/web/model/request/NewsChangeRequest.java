package ru.goltsov.education.web.model.request;

import lombok.Data;

@Data
public abstract class NewsChangeRequest {

    public long token; // id пользователя

}

