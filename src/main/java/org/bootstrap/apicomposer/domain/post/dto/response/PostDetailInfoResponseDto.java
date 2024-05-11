package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailListResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;
import org.bootstrap.apicomposer.domain.user.vo.SearchUserVo;

@Builder(access= AccessLevel.PRIVATE)
public record PostDetailInfoResponseDto(
        PostDetailResponseDto postInfo,
        UserDetailResponseDto postWriterInfo,
        CommentDetailListResponseDto commentInfo,
        UserDetailListResponseDto commentWriterInfo

){
    public static PostDetailInfoResponseDto of(PostDetailResponseDto postInfo,
                                               UserDetailResponseDto postWriterInfo,
                                               CommentDetailListResponseDto commentInfo,
                                               UserDetailListResponseDto commentWriterInfo) {

        return PostDetailInfoResponseDto.builder().
                postInfo(postInfo)
                .postWriterInfo(postWriterInfo)
                .commentInfo(commentInfo)
                .commentWriterInfo(commentWriterInfo)
                .build();
    }
}
