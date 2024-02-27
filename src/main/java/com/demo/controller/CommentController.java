package com.demo.controller;

import com.demo.payload.CommentDto;
import com.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("id") long postId){
        CommentDto dto = commentService.create(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId, @PathVariable("id") long commentId){
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> findByPostId(@PathVariable("postId") long postId){
        List<CommentDto> dtos = commentService.findByPostId(postId);
        return dtos;
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId, @PathVariable("id") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment is deleted!!", HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable("postId") long postId, @PathVariable("id") long commentId){
        CommentDto update = commentService.updateComment(commentDto, postId, commentId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}
