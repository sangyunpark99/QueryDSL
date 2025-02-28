package com.example.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class MemberDto {

    private String username;
    private int age;

    @QueryProjection // dto가 querydsl에 의존
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
