package com.itshow.demo.repository.customImpl;

import com.itshow.demo.domain.QMember;
import com.itshow.demo.dto.PagingDto;
import com.itshow.demo.dto.PostDto;
import com.itshow.demo.repository.custom.PostRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.itshow.demo.domain.QMember.*;
import static com.itshow.demo.domain.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory query;

    /**
     * first index == list entity
     * second index == count entity
     * @param pageable
     * @return
     */
    @Override
    public Page<PostDto> pagingPost(Pageable pageable) {
        List<PostDto> result = query.select(Projections.constructor(PostDto.class,
                        member.name, post.title, post.content, post.createdDate))
                .from(post).distinct()
                .leftJoin(post.createdBy, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(post.count())
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }
}
