package sa.lendo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sa.lendo.dto.response.Comment;
import sa.lendo.dto.response.Post;
import sa.lendo.dto.response.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${gorest.baseUrl}")
    private String baseUrl;

    @Value("${gorest.endpoints.users}")
    private String usersEndpoint;

    @Value("${gorest.endpoints.posts}")
    private String postsEndpoint;

    @Value("${gorest.endpoints.comments}")
    private String commentsEndpoint;

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        return restTemplate.getForObject(baseUrl + usersEndpoint, List.class);
    }

    public List<Post> getAllPosts() {
        return restTemplate.exchange(baseUrl + postsEndpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {}).getBody();
    }

    public List<Post> getPostsByUserId(Long userId) {
        List<Post> posts = getAllPosts();
        return posts.stream().filter(post -> post.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public List<Comment> getAllComments() {
        return restTemplate.exchange(baseUrl + commentsEndpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {}).getBody();
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        List<Comment> comments = getAllComments();
        return comments.stream().filter(comment -> comment.getPostId().equals(postId)).collect(Collectors.toList());
    }

}
