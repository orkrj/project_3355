package elice.webshopping.controller.home;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("!deploy")
@Controller
public class HomeController {

    @GetMapping("/")
    public String homeController() {
        return "redirect:/home/home.html";
    }
}
