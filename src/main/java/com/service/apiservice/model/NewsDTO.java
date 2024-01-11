package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "NEWS")
@NoArgsConstructor
public class NewsDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsid")
    private Integer newsId;

    @Column(name = "news_image")
    private String newsImage;

    @Column(name = "news_title")
    private String newsTitle;

    @Column(name = "news_description")
    private String newsDescription;

    @Column(name = "news_content")
    private String newsContent;

    @Column(name = "news_link")
    private String newsLink;

    @Column(name = "type")
    private Integer type;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "is_active")
    private Integer isActive = 1;
}