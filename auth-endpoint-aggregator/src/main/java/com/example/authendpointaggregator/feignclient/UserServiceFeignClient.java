package com.example.authendpointaggregator.feignclient;

import com.example.authendpointaggregator.dto.UserFullResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {

    @GetMapping("/inner")
    UserFullResponseDTO findByUsername(@RequestParam(value = "name") String username);
}
