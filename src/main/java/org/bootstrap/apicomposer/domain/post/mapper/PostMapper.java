package org.bootstrap.apicomposer.domain.post.mapper;

import org.bootstrap.apicomposer.domain.post.dto.response.PostCategoryResponseDto;
import org.bootstrap.apicomposer.domain.post.vo.CategoryPostVo;
import org.bootstrap.apicomposer.domain.user.vo.UserProfileVo;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostCategoryResponseDto toPostCategoryResponseDto(CategoryPostVo postInfo,
                                                             UserProfileVo userInfo) {
        return PostCategoryResponseDto.of(postInfo, userInfo);
    }
}
