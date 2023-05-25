package com.itshow.demo.domain;

import com.itshow.demo.domain.basicEntity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter
@Builder @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BasicEntity {

    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;


    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member writeBy;
}