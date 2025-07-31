package me.shinsunyoung.springbootdeveloper.dto;

import lombok.Getter;

@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse (Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
