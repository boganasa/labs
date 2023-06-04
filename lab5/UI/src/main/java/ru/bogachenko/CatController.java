package ru.bogachenko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bogachenko.OOPModel.CatOOP;
import ru.bogachenko.services.CatImpl;

@RestController
@RequestMapping("/api/cats")
public class CatController {
    AmqpTemplate rabbitTemplate;

    ObjectMapper objectMapper;


    @Autowired
    public CatController(CatImpl catService) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @GetMapping(value = "/{catId}"/*, produces = APPLICATION_JSON_VALUE*/)
    public ResponseEntity<CatOOP> getCatById(@PathVariable long catId) throws JsonProcessingException {

        CatOOP cat = new CatOOP();
        cat.setId(catId);
        String catWrapped = objectMapper.writeValueAsString(cat);
        return ResponseEntity.ok(objectMapper.readValue((String) rabbitTemplate.convertSendAndReceive("key", catWrapped), new TypeReference<>() {}));
    }

    @PostMapping (value = "/create")
    public ResponseEntity<CatOOP> create( @RequestBody CatOOP catOOP) throws JsonProcessingException {
        CatOOP cat = new CatOOP();
        cat.setName(cat.getName());
        cat.setBreed(catOOP.getBreed());
        String catWrapped = objectMapper.writeValueAsString(cat);
        return ResponseEntity.ok(objectMapper.readValue((String) rabbitTemplate.convertSendAndReceive("key", catWrapped), new TypeReference<>() {}));
    }

    @DeleteMapping(value = "/delete/{catId}")
    public void deleteCat(@PathVariable long catId) throws JsonProcessingException {
        CatOOP cat = new CatOOP();
        cat.setId(cat.getId());
        String catWrapped = objectMapper.writeValueAsString(cat);

        rabbitTemplate.convertSendAndReceive("key", catWrapped);

    }

}
