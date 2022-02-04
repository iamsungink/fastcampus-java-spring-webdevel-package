package com.example.put.controller;

import com.example.put.dto.PostRequestDTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PutApiController {

//    @PutMapping("/put")
//    public void put(@RequestBody PostRequestDTo requestDto) {
//        System.out.println(requestDto);
//    }

//    @PutMapping("/put")
//    public PostRequestDTo put(@RequestBody PostRequestDTo requestDto) {
//        System.out.println(requestDto);
//        return requestDto;
//    }

    @PutMapping("/put/{userId}")
    public PostRequestDTo put(@RequestBody PostRequestDTo requestDto, @PathVariable(name = "userId") Long id) {
        System.out.println(id);
        return requestDto;
    }
}
