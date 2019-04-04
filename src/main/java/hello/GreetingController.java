package hello;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	/**
	 * 
	 * @param name
	 * @param model
	 * @return
	 * 
	 * possible urls:
	 * 		http://localhost:8080/greeting
	 * 		http://localhost:8080/greeting?name=Raja
	 */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    /**
     * 
     * @param name
     * @param model
     * @return
     * 
     * possible urls:
	 * 		http://localhost:8080/users
     */
    @GetMapping("/users")
    public String getUsers(@RequestParam(name="city", required=false, defaultValue="Toronto") String name, Model model) {
        model.addAttribute("city", name);
        
        List<String> userList = new LinkedList<String>();
        userList.add("Raja");
        userList.add("Che");
        userList.add("Sowmya");   
        
        model.addAttribute("users", userList);
        
        return "users";
    }
}
