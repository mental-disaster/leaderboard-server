package com.project.server.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostDto {
    private String id;
    private String groupId;
}
