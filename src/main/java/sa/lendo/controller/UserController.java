package sa.lendo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.lendo.dto.response.Comment;
import sa.lendo.dto.response.Post;
import sa.lendo.dto.response.User;
import sa.lendo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/v2")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return userService.getAllPosts();
    }

    @GetMapping("/posts/{userId}")
    public List<Post> getPostsByUserId(@PathVariable("userId") Long userId) {
        return userService.getPostsByUserId(userId);
    }

    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return userService.getAllComments();
    }

    @GetMapping("/comments/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable("postId") Long postId) {
        return userService.getCommentsByPostId(postId);
    }
}
