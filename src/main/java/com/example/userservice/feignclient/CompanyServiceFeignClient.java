package com.example.userservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "company-service",
        path = "/api/companies",
        url = "http://company-service:8088"
)
public interface CompanyServiceFeignClient {

    @GetMapping("/exist-by-id/{companyId}")
    Boolean existById(@PathVariable("companyId") Long companyId);

    @GetMapping("/name-by-id/{id}")
    String getNameById(@PathVariable("id") Long id);

}
