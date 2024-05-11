package org.bootstrap.apicomposer.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailListResponseDto;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailResponseDto;
import org.bootstrap.apicomposer.domain.post.dto.response.PostDetailInfoResponseDto;
import org.bootstrap.apicomposer.domain.post.dto.response.SearchPostsResponseDto;
import org.bootstrap.apicomposer.domain.post.helper.PostHelper;
import org.bootstrap.apicomposer.domain.post.vo.SearchPostVo;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentDetailListResponseDto;
import org.bootstrap.apicomposer.domain.reply.helper.ReplyHelper;
import org.bootstrap.apicomposer.domain.reply.vo.PostCommentVo;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;
import org.bootstrap.apicomposer.domain.user.helper.UserHelper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostHelper postHelper;
    private final UserHelper userHelper;
    private final ReplyHelper replyHelper;

    public Mono<SearchPostsResponseDto> getSearchPosts(String text, ServerHttpRequest request) {
        Mono<PostDetailListResponseDto> searchPostVoMono = postHelper.getSearchPostResult(text, request.getHeaders());
        return searchPostVoMono.flatMap(result -> {
            List<Long> requestMembers = result.postList().stream()
                    .map(SearchPostVo::memberId)
                    .collect(Collectors.toList());
            Mono<UserDetailListResponseDto> searchUserVoMono = userHelper.getSearchUserResult(requestMembers, request.getHeaders());
            return searchUserVoMono.map(nextResult -> SearchPostsResponseDto.of(result, nextResult));
        });
    }

    //게시글 상세조회
    public Mono<PostDetailInfoResponseDto> getPostInfo(Long postId, Long postWriterId, ServerHttpRequest request) {
        //게시글 정보 불러옴
        Mono<PostDetailResponseDto> postDetailInfoMono = postHelper.getPostDetailInfoResult(postId, request.getHeaders());
        //게시글 작성자 불러옴
        Mono<UserDetailResponseDto> postWriterDetailInfoMono = userHelper.getUserDetailInfoResult(postWriterId, request.getHeaders());
        //게시글 달린 댓글 불러옴
        Mono<CommentDetailListResponseDto> commentsMono = replyHelper.getPostCommentResult(postId, request.getHeaders());
        return commentsMono.flatMap(comment ->{
            List<Long> requestMembers = comment.commentList().stream()
                    .map(PostCommentVo::memberId)
                    .collect(Collectors.toList());
            // 댓글 작성자 불러옴
            Mono<UserDetailListResponseDto> commentWriterMono = userHelper.getSearchUserResult(requestMembers, request.getHeaders());
            return Mono.zip(postDetailInfoMono, postWriterDetailInfoMono, Mono.just(comment), commentWriterMono)
                    .map(tuple -> {
                        PostDetailResponseDto postDetail = tuple.getT1();
                        UserDetailResponseDto postWriterDetail = tuple.getT2();
                        CommentDetailListResponseDto commentDetail = tuple.getT3();
                        UserDetailListResponseDto commentWriterDetail = tuple.getT4();
                        return PostDetailInfoResponseDto.of(postDetail, postWriterDetail, commentDetail, commentWriterDetail);
                    });
        });

    }

}
