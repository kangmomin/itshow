package com.itshow.demo.service;

import com.itshow.demo.common.Util;
import com.itshow.demo.domain.Member;
import com.itshow.demo.domain.Post;
import com.itshow.demo.domain.Reply;
import com.itshow.demo.exception.PostNotFoundException;
import com.itshow.demo.exception.ReplyNotFoundException;
import com.itshow.demo.repository.PostRepository;
import com.itshow.demo.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service @Slf4j
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public List<Reply> replyList(Long postId) {
        List<Reply> replyList = replyRepository.findAllByPostId(postId);

        return replyList;
    }

    public void writeReply(String content, Long postId) throws PostNotFoundException {
        Member member = Util.getLoginMember();
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) throw new PostNotFoundException();

        Reply reply = new Reply(Util.escaper(content), member, post.get());

        replyRepository.save(reply);
    }

    public void updateReply(String content, Long replyId) throws ReplyNotFoundException, IllegalAccessException {
        Member member = Util.getLoginMember();
        Optional<Reply> reply = replyRepository.findById(replyId);

        if (reply.isEmpty()) throw new ReplyNotFoundException();
        if (!Objects.equals(reply.get().getMember().getId(), member.getId()))
            throw new IllegalAccessException("Not the member's reply");

        reply.get().setContent(Util.escaper(content));
    }

    public void deleteReply(Long replyId) throws ReplyNotFoundException, IllegalAccessException {
        Member member = Util.getLoginMember();
        Optional<Reply> reply = replyRepository.findById(replyId);

        if (reply.isEmpty()) throw new ReplyNotFoundException();
        if (!Objects.equals(reply.get().getMember().getId(), member.getId()))
            throw new IllegalAccessException("Not the member's reply");

        replyRepository.delete(reply.get());
    }
}
