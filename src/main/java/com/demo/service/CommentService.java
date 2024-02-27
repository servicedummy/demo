package com.demo.service;

import com.demo.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(CommentDto commentDto, long postId);

    CommentDto getCommentById(long postId, long commentId);

    List<CommentDto> findByPostId(long postId);

    void deleteComment(long postId, long commentId);

    CommentDto updateComment(CommentDto commentDto, long postId, long commentId);
}
