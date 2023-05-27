package com.itshow.demo.domain;

import com.itshow.demo.domain.basicEntity.BasicEntity;
import com.itshow.demo.domain.basicEntity.BasicTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BasicTime {

    @Id @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setContent(String content) {
        this.content = content;
    }

    public Reply(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }
}
