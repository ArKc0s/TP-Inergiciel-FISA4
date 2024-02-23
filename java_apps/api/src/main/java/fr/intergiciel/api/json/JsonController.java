package fr.intergiciel.api.json;

import fr.intergiciel.api.kafka.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        messageProducer.sendMessage("topic1", message);
        return "Message sent: " + message;
    }

}
