package ru.bogachenko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bogachenko.OOPModel.OwnerOOP;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    AmqpTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;



    @Autowired
    public OwnerController(AmqpTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    @GetMapping(value = "/{ownerId}")
    public ResponseEntity<OwnerOOP> getOwnerById(@PathVariable long ownerId) throws JsonProcessingException {
        OwnerOOP owner = new OwnerOOP();
        owner.setId(ownerId);
        String ownerWrapped = objectMapper.writeValueAsString(owner);
        return ResponseEntity.ok(objectMapper.readValue((String) rabbitTemplate.convertSendAndReceive("key", ownerWrapped), new TypeReference<>() {}));
    }

    @PostMapping (value = "/create")
    public ResponseEntity<OwnerOOP> create( @RequestBody OwnerOOP ownerOOP) throws JsonProcessingException {
        OwnerOOP owner = new OwnerOOP();
        owner.setName(ownerOOP.getName());
        String ownerWrapped = objectMapper.writeValueAsString(owner);
        return ResponseEntity.ok(objectMapper.readValue((String) rabbitTemplate.convertSendAndReceive("key", ownerWrapped), new TypeReference<>() {}));
    }

    @DeleteMapping(value = "/delete/{ownerId}")
    public void deleteCat(@PathVariable long ownerId) throws JsonProcessingException {
        OwnerOOP owner = new OwnerOOP();
        owner.setId(ownerId);
        String ownerWrapped = objectMapper.writeValueAsString(owner);

        rabbitTemplate.convertSendAndReceive("key", ownerWrapped);

    }


}
