package kr.co.scheduler.community.controller;

import kr.co.scheduler.community.dtos.CommentReqDTO;
import kr.co.scheduler.community.dtos.PostReqDTO;
import kr.co.scheduler.community.entity.Comment;
import kr.co.scheduler.community.entity.Post;
import kr.co.scheduler.community.service.CommentService;
import kr.co.scheduler.community.service.PostService;
import kr.co.scheduler.global.dtos.ResponseDto;
import kr.co.scheduler.global.entity.Img;
import kr.co.scheduler.user.entity.User;
import kr.co.scheduler.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommunityApiController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @PostMapping("/api/community/write")
    public ResponseDto<Object> writePost(@RequestBody PostReqDTO.CREATE create, Principal principal) {

        postService.writePost(create, principal.getName());

        return ResponseDto.ofSuccessData(
                "게시물이 정상적으로 등록되었습니다.",
                null);
    }

    @PutMapping("/api/community/update/{post_id}")
    public ResponseDto<Object> updatePost(@RequestBody PostReqDTO.UPDATE update,
                                          Principal principal,
                                          @PathVariable(name = "post_id") Long id) {

        postService.updatePost(update, principal.getName(), id);

        return ResponseDto.ofSuccessData(
                "게시물이 정상적으로 수정되었습니다.",
                null);
    }

    @DeleteMapping("/api/community/delete/{post_id}")
    public ResponseDto<Object> deletePost(Principal principal, @PathVariable(name = "post_id") Long id) {

            Post post = postService.viewOneOfPost(id);

            postService.deleteImg(post);

            postService.deletePost(id, principal.getName());

            return ResponseDto.ofSuccessData(
                    "게시물이 정상적으로 삭제되었습니다.",
                    null);
    }

    /**
     * getProfileImg: 프로필이미지를 뷰로 전송
     * 뷰에서 바이트 단위의 데이터를 받기 위해서는
     * HttpStatus, HttpHeaders, HttpBody 를 모두 포함해야하기 때문에
     * ResponseDTO 가 아닌 ResponseEntity 를 응답한다.
     */
    @GetMapping("/api/community/post/profileImg/{email}")
    public ResponseEntity<?> getProfileImg(@PathVariable(name = "email") String email) throws IOException {

        User user = userService.findUser(email);

        if(user.getProfileImgPath() == null) {

            InputStream inputStream = getClass().getResourceAsStream("/static/image/profile-spap.png");

            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
            inputStream.close();

            return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        } else {

            InputStream inputStream = new FileInputStream(user.getProfileImgPath());

            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
            inputStream.close();

            return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        }
    }

    /**
     * uploadPostImg: 게시글 이미지를 등록 및 수정
     * 1. 업로드 된 게시글 이미지 등록
     */
    @PostMapping("/api/community/postImg/upload")
    public ResponseEntity<?> uploadPostImg(@RequestParam("file") MultipartFile uploadImg, Principal principal) throws IOException {

        String uploadFileName = postService.uploadImg(uploadImg, principal.getName());

        return ResponseEntity.ok("/api/community/postImg/get?uploadFileName=" + uploadFileName);
    }

    @GetMapping("/api/community/postImg/get")
    public ResponseEntity<?> getPostImg(@RequestParam String uploadFileName) throws IOException {

        Img img = postService.findImg(uploadFileName);

        InputStream inputStream = new FileInputStream(img.getImgPath());
        byte[] imageByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    /**
     * writeComment: 댓글 등록
     */
    @PostMapping("/api/community/post/comment/{post_id}")
    public ResponseDto<?> writeComment(@PathVariable(name = "post_id") Long id, @RequestBody CommentReqDTO.CREATE create, Principal principal) {

        commentService.writeComment(id, create, principal.getName());

        return ResponseDto.ofSuccessData(
                "댓글이 정상적으로 등록되었습니다.",
                null);
    }

    @PutMapping("/api/community/post/comment/update/{comment_id}")
    public ResponseDto<?> updateComment(@PathVariable(name = "comment_id") Long id, @RequestBody CommentReqDTO.UPDATE update, Principal principal) {
        
        User user = userService.findUser(principal.getName());
        Comment comment = commentService.findComment(id);

        if(!user.getEmail().equals(comment.getUser().getEmail())) {

            return ResponseDto.ofFailMessage(
                    HttpStatus.BAD_REQUEST.value(),
                    "댓글 수정은 댓글 작성자만 수행할 수 있습니다.");
        }

        commentService.updateComment(id, update);

        return ResponseDto.ofSuccessData(
                "댓글이 정상적으로 수정되었습니다.",
                null);
    }

    @DeleteMapping("/api/community/post/comment/delete/{comment_id}")
    public ResponseDto<?> deleteComment(@PathVariable(name = "comment_id") Long id, Principal principal) {

        User user = userService.findUser(principal.getName());
        Comment comment = commentService.findComment(id);

        if(!user.getEmail().equals(comment.getUser().getEmail())) {

            return ResponseDto.ofFailMessage(
                    HttpStatus.BAD_REQUEST.value(),
                    "댓글 삭제는 댓글 작성자만 수행할 수 있습니다.");
        }

        commentService.deleteComment(id);

        return ResponseDto.ofSuccessData(
                "댓글이 정상적으로 삭제되었습니다.",
                null);
    }
}