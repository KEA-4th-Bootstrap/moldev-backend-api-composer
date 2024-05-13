package org.bootstrap.apicomposer.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.apicomposer.domain.post.dto.response.*;
import org.bootstrap.apicomposer.domain.post.helper.PostHelper;
import org.bootstrap.apicomposer.domain.post.mapper.PostMapper;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.domain.post.vo.SearchPostVo;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentCountResponseDto;
import org.bootstrap.apicomposer.domain.reply.helper.ReplyHelper;
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
    private final PostMapper postMapper;
    private final UserHelper userHelper;
    private final ReplyHelper replyHelper;

    public Mono<PostCategoryResponseDto> getCategoryPost(String moldevId,
                                                         CategoryType type,
                                                         ServerHttpRequest request) {
        Mono<CategoryPostVo> getCategoryPost = postHelper.getCategoryPost(moldevId, request.getHeaders());
        Mono<UserDetailResponseDto> userDetailResponseDtoMono = userHelper.getUserProfileVo(moldevId, request.getHeaders());
        return Mono.zip(getCategoryPost, userDetailResponseDtoMono).flatMap(tuple ->
                Mono.just(postMapper.toPostCategoryResponseDto(tuple.getT1(), tuple.getT2()))
        );
    }

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
    public Mono<PostDetailTotalResponseDto> getPostInfo(Long postId, Long postWriterId, ServerHttpRequest request) {
        //게시글 정보 불러옴
        Mono<PostDetailResponseDto> postDetailInfoMono = postHelper.getPostDetailInfoResult(postId, request.getHeaders());
        //게시글 작성자 불러옴
        Mono<UserDetailResponseDto> postWriterDetailInfoMono = userHelper.getUserDetailInfoResult(postWriterId, request.getHeaders());
        //게시글 달린 댓글 개수 불러옴
        Mono<CommentCountResponseDto> commentCountMono = replyHelper.getPostCommentCount(postId, request.getHeaders());
        return Mono.zip(postDetailInfoMono, postWriterDetailInfoMono, commentCountMono).flatMap(tuple ->
                Mono.just(PostDetailTotalResponseDto.of(tuple.getT1(), tuple.getT2(), tuple.getT3()))
        );

    }

}
