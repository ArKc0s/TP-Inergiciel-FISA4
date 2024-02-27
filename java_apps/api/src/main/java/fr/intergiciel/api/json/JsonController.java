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
//        System.out.println("Post read xml");
//        System.out.println(xmlMessage);
//
//        // Créer une instance de XmlMapper pour lire le XML dans une structure de données générique
//        XmlMapper xmlMapper = new XmlMapper();
//        JsonNode mainNode = null;
//        try {
//            mainNode = xmlMapper.readTree(xmlMessage.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Créer une instance de ObjectMapper pour convertir la structure de données générique en JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = null;
//        try {
//            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mainNode);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Json:" + json);
//
//
//        // Création d'une patient
//        ObjectNode patient = objectMapper.createObjectNode();
//        // Récuperation des informations
//
//        JsonNode pid5Array = mainNode.path("PID").path("PID.5");
//
//        String birthName = null;
//        String legalName = null;
//
//        if (pid5Array.isArray()) {
//            for (JsonNode node : pid5Array) {
//                String nameType = node.path("PID.5.7").asText();
//                if ("D".equals(nameType)) {
//                    birthName = node.path("PID.5.1").asText();
//                } else if ("L".equals(nameType)) {
//                    legalName = node.path("PID.5.1").asText();
//                }
//            }
//        }
//
//        // Traiter les informations selon les cas
//        System.out.println("Nom de naissance: " + birthName);
//        System.out.println("Nom légal: " + legalName);
//        // Ajout des informations dans la patient
//        if (birthName != null) {
//            patient.put("BirthName", birthName);
//        }
//        if (legalName != null) {
//            patient.put("LegalName", legalName);
//        }
//
//        System.out.println(patient);

//        patient.put("BirthDate", mainNode.path("PID").path("PID.7").path("PID.7.1"));
//        patient.put("FirstName", mainNode.path("PID").path("PID.5").path("PID.5.2"));
//        patient.put("Prefix", mainNode.path("PID").path("PID.5").path("PID.5.5"));
//        patient.put("ID", mainNode.path("PID").path("PID.3").path("PID.3.1"));
//
//        System.out.println(patient);
//
//        ObjectNode movements = objectMapper.createObjectNode();
//
//        movements.put("Service", mainNode.path("PV1").path("PV1.3").path("PV1.3.1"));
//        movements.put("Room", mainNode.path("PV1").path("PV1.3").path("PV1.3.2"));
//        movements.put("Bed", mainNode.path("PV1").path("PV1.3").path("PV1.3.3"));
//
//        ObjectNode stay = objectMapper.createObjectNode();
//
//        stay.put("NumUniqueSejour",mainNode.path("PV1").path("PV1.19").path("PV1.19.1"));
//        stay.put("AdmiDate",mainNode.path("PV1").path("PV1.44").path("PV1.44.1"));
//        stay.put("DeschargeDate",mainNode.path("PV1").path("PV1.45").path("PV1.45.1"));
//
//        ObjectNode jsonFinal = objectMapper.createObjectNode();
//        jsonFinal.put("Patient", patient);
//        jsonFinal.put("Movements", movements);

        // Retourner le JSON généré
        messageProducer.sendMessage("topic1", jsonMessage);
//        return "Message sent: " + jsonFinal;
//        return "Message sent: " + mainNode;
        return "sent : "+ jsonMessage;

    }
}
