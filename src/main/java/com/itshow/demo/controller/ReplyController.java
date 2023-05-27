package com.itshow.demo.controller;

import com.itshow.demo.domain.Reply;
import com.itshow.demo.dto.Result;
import com.itshow.demo.dto.reply.ReplyDto;
import com.itshow.demo.dto.reply.ReplyContentDto;
import com.itshow.demo.exception.PostNotFoundException;
import com.itshow.demo.exception.ReplyNotFoundException;
import com.itshow.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/reply/{postId}")
    public ResponseEntity<Result<List<ReplyDto>>> replyList(@PathVariable Long postId) {
        try {
            List<Reply> replyList = replyService.replyList(postId);

            List<ReplyDto> list = replyList.stream().map(reply -> {
                boolean isEdit = reply.getCreatedDate().isEqual(reply.getUpdatedDate());
                return ReplyDto.builder()
                        .content(reply.getContent())
                        .memberName(reply.getMember().getName())
                        .isEdit(!isEdit)
                        .createdAt(reply.getCreatedDate())
                        .build();
            }).toList();

            return new ResponseEntity<>(new Result(list, false), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reply/write/{postId}")
    public ResponseEntity<Result> replyWrite(@PathVariable("postId") Long postId,
                                             @RequestBody ReplyContentDto writeReplyDto) {
        try {

            replyService.writeReply(writeReplyDto.getContent(), postId);

            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reply/update/{replyId}")
    public ResponseEntity<Result> replyUpdate(@PathVariable("replyId") Long replyId,
                                             @RequestBody ReplyContentDto replyContentDto) {
        try {
            replyService.updateReply(replyContentDto.getContent(), replyId);
            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (ReplyNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reply/delete/{replyId}")
    public ResponseEntity<Result> deleteUpdate(@PathVariable("replyId") Long replyId) {
        try {
            replyService.deleteReply(replyId);
            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (ReplyNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
