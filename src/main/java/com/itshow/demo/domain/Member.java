package com.itshow.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itshow.demo.domain.basicEntity.BasicTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BasicTime {

    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String loginId;
    private String password;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "writeBy")
    @JsonIgnore
    private List<Post> post;

    @OneToMany(fetch = FetchType.LAZY,
                cascade = CascadeType.ALL,
                mappedBy = "member")
    @JsonIgnore
    private List<Reply> reply;


    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "member")
    @JsonIgnore
    private List<Favorite> favorite;

    public Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }
}
