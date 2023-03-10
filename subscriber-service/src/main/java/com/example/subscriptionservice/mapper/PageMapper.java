package com.example.subscriptionservice.mapper;

import com.example.subscriptionservice.dto.response.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PageMapper<T> {

    public PageDTO<T> mapToDto(Page<T> page) {
        return new PageDTO<>(
                page.getContent(),
                page.getTotalElements(),
                page.hasNext()
        );
    }

}
