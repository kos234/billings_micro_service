package kos.progs.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {
    @PostMapping("/test/ping")
    public String ping(){
        return "pong";
    }
}
