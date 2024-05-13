package org.bootstrap.apicomposer.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.apicomposer.domain.reply.dto.response.CommentCountResponseDto;
import org.bootstrap.apicomposer.domain.user.dto.response.UserDetailResponseDto;

@Builder(access= AccessLevel.PRIVATE)
public record PostDetailTotalResponseDto(
        PostDetailResponseDto postInfo,
        UserDetailResponseDto postWriterInfo,
        Long commentCount
){
    public static PostDetailTotalResponseDto of(PostDetailResponseDto postInfo,
                                                UserDetailResponseDto postWriterInfo,
                                                CommentCountResponseDto commentCountResponseDto) {

        return PostDetailTotalResponseDto.builder()
                .postInfo(postInfo)
                .postWriterInfo(postWriterInfo)
                .commentCount(commentCountResponseDto.commentCount())
                .build();
    }
}
