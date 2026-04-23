package com.myorg.vibehub.service;


import com.myorg.vibehub.dto.request.PostRequestDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.PostResponseDto;
import com.myorg.vibehub.model.Post;
import com.myorg.vibehub.model.User;
import com.myorg.vibehub.repository.PostRepository;
import com.myorg.vibehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public GenericResponseDto uploadPost(PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId()).orElse(null);
        GenericResponseDto genericResponseDto = new GenericResponseDto();

        if(user == null)
        {
            genericResponseDto.setIsSuccess(false);
            genericResponseDto.setMessage("User id: " + postRequestDto.getUserId() + " doesn't exist");
        }

        if(postRequestDto.getCaption() == null || postRequestDto.getCaption().isBlank())
        {
            genericResponseDto.setIsSuccess(false);
            genericResponseDto.setMessage("Caption can not be blank or null");
        }
        Post post = new Post();

        //convert PostRequestDto to Post
        post.setCaption(postRequestDto.getCaption());
        post.setUser(user);

        //Save post in DB
        Post storedPost=postRepository.save(post);

        genericResponseDto.setIsSuccess(true);
        genericResponseDto.setMessage("Posted Successfully");

        return genericResponseDto;
    }

    @Override
    public PostResponseDto getPost(Long id) {

        Post post=postRepository.findById(id).orElse(null);
        return mapPostToPostResponseDto(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> postResponseDtoList = new LinkedList<>();

        for(Post post:postList) {
            PostResponseDto postResponseDto= mapPostToPostResponseDto(post);
            postResponseDtoList.add(postResponseDto);
        }
        return postResponseDtoList;
    }

    @Override
    public PostResponseDto updatePost(Long id,PostRequestDto postRequestDto) {

        Post post = postRepository.findById(id).orElse(null);
        mapPostRequestDtoToPost(post.getUser(), postRequestDto, post);
        postRepository.save(post);
        return mapPostToPostResponseDto(post);
    }

    @Override
    public GenericResponseDto removePost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        if(post != null){
            String caption= post.getCaption();
            genericResponseDto.setIsSuccess(true);
            genericResponseDto.setMessage("Post caption- " + caption + " is removed successfully");
        }
        else{
            genericResponseDto.setIsSuccess(false);
            genericResponseDto.setMessage("The post id- " + post.getId()+ " does not exist!");
        }
        return genericResponseDto;
    }

    @Override
    public GenericResponseDto likePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        GenericResponseDto genericResponseDto = new GenericResponseDto();

        if(post == null){
            genericResponseDto.setIsSuccess(false);
            genericResponseDto.setMessage("post id: " +postId+ "does not exist.");
        }

        post.setLikeCount(post.getLikeCount()+1);
        postRepository.save(post);


        return null;
    }

    //HELPER METHODS-

    private Post mapPostRequestDtoToPost(User user,PostRequestDto postRequestDto, Post post)
    {
        post.setCaption(postRequestDto.getCaption());
        post.setUser(user);

        return post;
    }

    private PostResponseDto mapPostToPostResponseDto(Post post){
        PostResponseDto postResponseDto=new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setCaption(post.getCaption());
        postResponseDto.setUser(post.getUser());
        postResponseDto.setLikeCount(post.getLikeCount());
        postResponseDto.setCommentCount(post.getCommentCount());
        postResponseDto.setShareCount(post.getShareCount());

        return postResponseDto;
    }

    private List<PostResponseDto> mapPostListToPostResponseDtoList(List<Post> postList){
        List<PostResponseDto> postResponseDtoList = new LinkedList<>();

        for(Post post : postList)
        {
            postResponseDtoList.add(mapPostToPostResponseDto(post));
        }
        return postResponseDtoList;
    }
}
