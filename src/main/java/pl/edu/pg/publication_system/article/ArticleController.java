package pl.edu.pg.publication_system.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/article")
@RestController
public class ArticleController {

    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }
    
}

