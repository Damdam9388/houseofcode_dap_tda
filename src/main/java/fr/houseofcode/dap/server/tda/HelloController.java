package fr.houseofcode.dap.server.tda;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tda
 *
 */
@RestController
public class HelloController {
/**
 *
 * @return
 */
    @RequestMapping("/")
    public String index() {
        return "Hey ! Bienvenue Spring Boot!";
    }

}
