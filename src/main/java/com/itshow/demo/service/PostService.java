package com.itshow.demo.service;

import com.itshow.demo.common.Util;
import com.itshow.demo.domain.Favorite;
import com.itshow.demo.domain.Member;
import com.itshow.demo.domain.Post;
import com.itshow.demo.dto.post.PagingDto;
import com.itshow.demo.dto.post.PostDto;
import com.itshow.demo.dto.post.UpdatePostDto;
import com.itshow.demo.dto.post.WritePostDto;
import com.itshow.demo.exception.FavoriteNotFoundException;
import com.itshow.demo.exception.PostNotFoundException;
import com.itshow.demo.repository.FavoriteRepository;
import com.itshow.demo.repository.MemberRepository;
import com.itshow.demo.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    public PagingDto<List<PostDto>> getPostList(Pageable page) {

        Page<PostDto> paging = postRepository.pagingPost(page);
        return new PagingDto<List<PostDto>>(paging.getContent(),
                paging.getTotalElements(),
                paging.getTotalPages());
    }

    public void writePost(WritePostDto writePostDto) {
        Member loginMember = Util.getLoginMember();
        Post post = Post.builder()
                .title(writePostDto.getTitle())
                .content(writePostDto.getContent())
                .writeBy(loginMember)
                .build();
        postRepository.save(post);
    }

    public void updatePost(UpdatePostDto updatePostDto) throws PostNotFoundException, IllegalAccessException {
        Optional<Post> post = postRepository.findById(updatePostDto.getId());
        Member loginMember = Util.getLoginMember();
        Member member = memberRepository.findByLoginId(loginMember.getLoginId());

        if (post.isEmpty()) throw new PostNotFoundException();
        if (!Objects.equals(member.getLoginId(), post.get().getCreatedBy()))
            throw new IllegalAccessException("not a member's post");

        post.get().setTitle(updatePostDto.getTitle());
        post.get().setContent(updatePostDto.getContent());
    }

    public void deletePost(Long postId) throws PostNotFoundException, IllegalAccessException {
        Optional<Post> post = postRepository.findById(postId);
        Member loginMember = Util.getLoginMember();
        Member member = memberRepository.findByLoginId(loginMember.getLoginId());

        if (post.isEmpty()) throw new PostNotFoundException();
        if (!Objects.equals(member.getLoginId(), post.get().getCreatedBy()))
                    throw new IllegalAccessException("not a member's post");

        postRepository.delete(post.get());
    }

    public void favoritPost(Long postId) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(postId);
        Member member = Util.getLoginMember();

        if (post.isEmpty()) throw new PostNotFoundException();

        Favorite favorit = new Favorite(post.get(), member);
        favoriteRepository.save(favorit);
    }
    public void defavoritPost(Long favoriteId) throws FavoriteNotFoundException, IllegalAccessException {
        Optional<Favorite> favorite = favoriteRepository.findById(favoriteId);
        Member member = Util.getLoginMember();

        if (favorite.isEmpty()) throw new FavoriteNotFoundException();
        if (!Objects.equals(favorite.get().getMember().getId(), member.getId()))
            throw new IllegalAccessException("not the member's favorite");

        favoriteRepository.delete(favorite.get());
    }
}
