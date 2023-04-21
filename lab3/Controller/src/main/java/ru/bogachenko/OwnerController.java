package ru.bogachenko;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bogachenko.exceptions.CatNotFoundException;
import ru.bogachenko.exceptions.OwnerNotFoundException;
import ru.bogachenko.models.Color;
import ru.bogachenko.responses.CatOOP;
import ru.bogachenko.responses.OwnerOOP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    OwnerImpl ownerService;

    @Autowired
    public OwnerController(OwnerImpl ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public List<OwnerOOP> findAll() {
        return ownerService.findAllOwners();
    }

    @GetMapping(value = "/{ownerId}")
    public OwnerOOP getOwnerById(@PathVariable long ownerId) {
        if (ownerService.getOwnerById(ownerId) == null)
        {
            throw new OwnerNotFoundException(ownerId);
        }
        OwnerOOP owner = new OwnerOOP(ownerService.getOwnerById(ownerId));
        return owner;
    }

    @GetMapping (value = "/create", params = {"birthday"})
    public String create( @RequestParam String birthday,
                          @RequestParam(required = false, defaultValue = "Anonimus") String name) {
        OwnerOOP owner = new OwnerOOP();
        owner.setName(name);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date;
        try {
            date = formatter.parse(birthday);
        } catch (ParseException e) {
            return "Error with birthday";
        }
        owner.setBirthday(date);
        ownerService.createOwner(owner);
        return owner + "is created";
    }

    @DeleteMapping(value = "/delete/{ownerId}", produces = APPLICATION_JSON_VALUE)
    public void deleteOwner(@PathVariable long ownerId) {
        if (ownerService.getOwnerById(ownerId) == null)
        {
            throw new OwnerNotFoundException(ownerId);
        }
        ownerService.deleteOwner(ownerId);
    }

    @GetMapping(value = "/tame", params = {"ownerId", "catId"})
    public String toTame(@RequestParam String ownerId, @RequestParam String catId){
        long catID = Long.valueOf(catId).longValue();
        long ownerID = Long.valueOf(ownerId).longValue();

        ownerService.addNewCatToOwner(ownerID, catID);

        return "Now this cat have new owner:\n";
    }
}
