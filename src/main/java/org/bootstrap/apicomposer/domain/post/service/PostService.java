package org.bootstrap.apicomposer.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.apicomposer.domain.post.dto.response.*;
import org.bootstrap.apicomposer.domain.post.helper.PostHelper;
import org.bootstrap.apicomposer.domain.post.type.CategoryType;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentCountResponseDto;
import org.bootstrap.apicomposer.domain.reply.helper.ReplyHelper;
import org.bootstrap.apicomposer.domain.user.dto.response.TrendingMembersListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.TrendingMembersResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.helper.UserHelper;
import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;
import org.bootstrap.apicomposer.global.common.response.ApiResponse;
import org.bootstrap.apicomposer.global.common.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.bootstrap.apicomposer.global.utils.MemberIdUtils.getMemberIds;
import static org.bootstrap.apicomposer.global.utils.MoldevIdUtils.getMoldevIds;

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

    public Mono<ApiResponse<?>> getTrendIsland(ServerHttpRequest request) {
        return userHelper.getTrendIslands(request.getHeaders())
                .flatMapMany(responseEntity -> {
                    TrendingMembersListResponseDto trendingMembersList = responseEntity.getBody();
                    List<TrendingMembersResponseDto> trendingMembers = trendingMembersList.trendingMembersResponseDtos();
                    return Flux.fromIterable(trendingMembers);
                })
                .flatMap(member -> {
                    String moldevId = member.memberProfileResponseDto().moldevId();
                    return postHelper.getTrendIslandPostList(moldevId, request.getHeaders())
                            .map(postResponseEntity -> {
                                RecentPostsResponseListDto recentPosts = postResponseEntity.getBody();
                                return TrendIslandResponseDto.of(recentPosts, TrendingMembersListResponseDto.of(List.of(member)));
                            });
                })
                .collectList()
                .map(trendIslandResponseDtos -> {
                    TrendIslandListResponseDto responseDto = TrendIslandListResponseDto.of(trendIslandResponseDtos);
                    return ApiResponse.of(SuccessCode.SUCCESS, responseDto);
                });
    }

    public Mono<ApiResponse<?>> getTrendPost(ServerHttpRequest request) {
        Mono<ResponseEntity<TrendPostDetailListResponseDto>> trendPostList = postHelper.getTrendPostList(request.getHeaders());
        return trendPostList.flatMap(result -> {
            List<String> requestMoldevIds = getMoldevIds(result.getBody().postList());
            Mono<ResponseEntity<TrendUserDetailListResponseDto>> searchUserVoMono = userHelper.getUserProfileVoByMoldevIds(requestMoldevIds, request.getHeaders());
            return searchUserVoMono.map(nextResult -> {
                TrendPostResponseDto responseDto = TrendPostResponseDto.of(result.getBody(), nextResult.getBody());
                return ApiResponse.of(SuccessCode.SUCCESS, responseDto);
            });
        });
    }

}
