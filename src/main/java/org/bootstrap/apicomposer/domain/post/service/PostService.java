package org.bootstrap.apicomposer.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.post.dto.response.*;
import org.bootstrap.apicomposer.domain.post.helper.PostHelper;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.domain.post.vo.PostDetailWithRedisVo;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentCountResponseDto;
import org.bootstrap.apicomposer.domain.reply.helper.ReplyHelper;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.helper.UserHelper;
import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.bootstrap.apicomposer.global.common.response.SuccessCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static org.bootstrap.apicomposer.global.utils.MemberIdUtils.getMemberIds;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostHelper postHelper;
    private final UserHelper userHelper;
    private final ReplyHelper replyHelper;

    public Mono<ApiResponse<?>> getCategoryPost(String moldevId,
                                                CategoryType type,
                                                ServerHttpRequest request) {
        Mono<ResponseEntity<CategoryPostVo>> getCategoryPost = postHelper.getCategoryPost(moldevId, type, request.getHeaders());
        Mono<ResponseEntity<UserProfileVo>> userDetailResponseDtoMono = userHelper.getUserProfileVo(moldevId, request.getHeaders());
        return Mono.zip(getCategoryPost, userDetailResponseDtoMono).flatMap(tuple -> {
            PostCategoryResponseDto responseDto = PostCategoryResponseDto.of(tuple.getT1().getBody(), tuple.getT2().getBody());
            return Mono.just(ApiResponse.of(SuccessCode.SUCCESS, responseDto));
        });
    }

    public Mono<ApiResponse<?>> getSearchPosts(String title,
                                               Integer size,
                                               Integer page,
                                               ServerHttpRequest request) {
        Mono<ResponseEntity<PostDetailListResponseDto>> searchPostVoMono = postHelper.getSearchPostResult(title, size, page, request.getHeaders());
        return searchPostVoMono.flatMap(result -> {
            List<Long> requestMembers = getMemberIds(result.getBody().postList());
            Mono<ResponseEntity<UserDetailListResponseDto>> searchUserVoMono = userHelper.getSearchUserResult(requestMembers, request.getHeaders());
            return searchUserVoMono.map(nextResult -> {
                SearchPostsResponseDto responseDto = SearchPostsResponseDto.of(result.getBody(), nextResult.getBody());
                return ApiResponse.of(SuccessCode.SUCCESS, responseDto);
            });
        });
    }

    public Mono<ApiResponse<?>> getPostInfo(Long postId, String moldevId, ServerHttpRequest request) {
        Mono<ResponseEntity<PostDetailResponseDto>> postDetailInfoMono = postHelper.getPostDetailInfoResult(postId, request.getHeaders());
        Mono<ResponseEntity<UserProfileVo>> postWriterDetailInfoMono = userHelper.getUserProfileVo(moldevId, request.getHeaders());
        Mono<ResponseEntity<CommentCountResponseDto>> commentCountMono = replyHelper.getPostCommentCount(postId, request.getHeaders());
        return Mono.zip(postDetailInfoMono, postWriterDetailInfoMono, commentCountMono).flatMap(tuple -> {
            PostDetailTotalResponseDto responseDto = PostDetailTotalResponseDto.of(tuple.getT1().getBody(), tuple.getT2().getBody(), tuple.getT3().getBody());
            return Mono.just(ApiResponse.of(SuccessCode.SUCCESS, responseDto));
        });
    }

    public Mono<ApiResponse<?>> getTrendPost(ServerHttpRequest request) {
        return postHelper.getTrendPostList(request.getHeaders())
                .flatMapMany(this::extractPostDetails)
                .flatMap(postDetail -> fetchUserProfile(postDetail, request.getHeaders()))
                .collectList()
                .map(this::mergePostAndUserDetails)
                .map(responseDto -> ApiResponse.of(SuccessCode.SUCCESS, responseDto));
    }

    private Flux<PostDetailWithRedisVo> extractPostDetails(ResponseEntity<TrendPostDetailListResponseDto> responseEntity) {
        List<PostDetailWithRedisVo> postDetails = responseEntity.getBody().postList();
        return Flux.fromIterable(postDetails);
    }

    private Mono<TrendPostResponseDto> fetchUserProfile(PostDetailWithRedisVo postDetail, HttpHeaders headers) {
        String moldevId = postDetail.postInfo().moldevId();
        return userHelper.getUserProfileVo(moldevId, headers)
                .map(userDetailResponse -> {
                    UserProfileVo userDetail = userDetailResponse.getBody();
                    return TrendPostResponseDto.of(
                            TrendPostDetailListResponseDto.of(List.of(postDetail)),
                            TrendUserDetailListResponseDto.of(List.of(userDetail))
                    );
                });
    }

    private TrendPostResponseDto mergePostAndUserDetails(List<TrendPostResponseDto> trendPostResponseDtos) {
        List<PostDetailWithRedisVo> postList = trendPostResponseDtos.stream()
                .flatMap(dto -> dto.postInfo().postList().stream())
                .collect(Collectors.toList());

        List<UserProfileVo> userList = trendPostResponseDtos.stream()
                .flatMap(dto -> dto.userInfo().userList().stream())
                .collect(Collectors.toList());

        return TrendPostResponseDto.of(
                TrendPostDetailListResponseDto.of(postList),
                TrendUserDetailListResponseDto.of(userList)
        );
    }
}
