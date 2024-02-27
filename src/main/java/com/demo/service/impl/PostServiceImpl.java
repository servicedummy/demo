package com.demo.service.impl;

import com.demo.entity.Post;
import com.demo.exceptions.ResourceNotFoundException;
import com.demo.payload.PostDto;
import com.demo.repository.PostRepository;
import com.demo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post save = postRepo.save(post);

        return mapToDto(save);
    }

    @Override
    public PostDto getPostsById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        return mapToDto(post);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts = postRepo.findAll(pageable);

        List<Post> content = posts.getContent();

        return content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        Post entity = mapToEntity(postDto);
        entity.setId(post.getId());

        Post save = postRepo.save(entity);

        return mapToDto(save);
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
}
