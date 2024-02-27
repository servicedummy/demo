package com.demo.service.impl;

import com.demo.entity.Comment;
import com.demo.entity.Post;
import com.demo.exceptions.ResourceNotFoundException;
import com.demo.payload.CommentDto;
import com.demo.repository.CommentRepository;
import com.demo.repository.PostRepository;
import com.demo.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto create(CommentDto commentDto, long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment save = commentRepo.save(comment);

        return mapToDto(save);
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(commentId)
        );
        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> findByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        List<Comment> comments = commentRepo.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(commentId)
        );

        commentRepo.deleteById(commentId);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(commentId)
        );

        Comment entity = mapToEntity(commentDto);
        entity.setId(comment.getId());
        entity.setPost(post);

        Comment save = commentRepo.save(entity);

        return mapToDto(save);
    }

    Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    CommentDto mapToDto(Comment comment) {
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }
}
