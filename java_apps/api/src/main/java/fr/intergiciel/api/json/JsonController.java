package fr.intergiciel.api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.intergiciel.api.kafka.MessageProducer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RestController
@RequestMapping("/json")
public class JsonController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String jsonMessage) {
        System.out.println("Post read json" + jsonMessage);

        // Retourner le JSON généré
        messageProducer.sendMessage("topic1", jsonMessage);
        System.out.println("Done");
        return "sent : "+ jsonMessage;
    }
}
