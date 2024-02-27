package com.demo.service;

import com.demo.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPostsById(long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    void deletePost(long id);

    PostDto updatePost(PostDto postDto, long id);
}
