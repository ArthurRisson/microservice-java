package br.edu.atitus.greetingservice.controllers;

import br.edu.atitus.greetingservice.configs.GreetingConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {


//    @Value("${greeting-service.default-name}")
//    private String defaultName;
//    @Value("${greeting-service.greeting}")
//    private String greeting;

    private final GreetingConfig config;
    //injeção de dependência
    public GreetingController(GreetingConfig config) {
        this.config = config;
    }

    @GetMapping({"", "/"})
    public String getGreeting(
            @RequestParam(required = false) String name)
    {
        if (name ==null || name.isEmpty()){
            name = config.getDefaultName();
        }
        String greetingReturn = String.format("%s %s!!!",config.getGreeting()   , name);
        return greetingReturn;
    }
    @GetMapping("/{name}")
    public String getGreetingPath(@PathVariable String name){
        String greetingreturn = String.format("%s %s!!!",config.getGreeting(), name);
        return greetingreturn;
    }

    public record GreetingRequest(String name) {}


    @PostMapping({"", "/"})
    public String postGreeting(@RequestBody GreetingRequest request) {

        String name = request.name();

        if (name == null || name.isEmpty()){
            name = config.getDefaultName();
        }


        return String.format("%s %s!!!", config.getGreeting(), name);
    }
}
