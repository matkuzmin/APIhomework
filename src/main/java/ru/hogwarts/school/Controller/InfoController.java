package ru.hogwarts.school.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController()
public class InfoController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/getPort")
    public String getPort(){
        return port;
    }
}
