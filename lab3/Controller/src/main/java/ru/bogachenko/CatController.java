package ru.bogachenko;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bogachenko.exceptions.CatNotFoundException;
import ru.bogachenko.models.Color;
import ru.bogachenko.responses.CatOOP;
import ru.bogachenko.responses.OwnerOOP;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/cats")
public class CatController {
    CatImpl catService;

    @Autowired
    public CatController(CatImpl catService) {
        this.catService = catService;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public List<CatOOP> findAll() {
        return catService.findAllCats();
    }

    @GetMapping(value = "/{catId}"/*, produces = APPLICATION_JSON_VALUE*/)
    public CatOOP getCatById(@PathVariable long catId) {
        if (catService.getCatById(catId) == null)
        {
            throw new CatNotFoundException(catId);
        }
        CatOOP cat = new CatOOP(catService.getCatById(catId));
        return cat;
    }

    @GetMapping (value = "/create", params = {"name", "birthday"})
    public String create( @RequestParam String name, @RequestParam String birthday,
                        @RequestParam(required = false, defaultValue = "Oblezlaya") String color,
                        @RequestParam(required = false, defaultValue = "Bezdomnaya") String breed) {
        CatOOP cat = new CatOOP();
        cat.setName(name);
        cat.setBreed(breed);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date;
        try {
            date = formatter.parse(birthday);
        } catch (ParseException e) {
            return "Error with birthday";
        }
        cat.setBirthday(date);
        cat.setColor(Color.valueOf(color.toUpperCase()));
        catService.createNewCat(cat);
        return cat.toString() + "is created";
    }

    @DeleteMapping(value = "/delete/{catId}")
    public void deleteCat(@PathVariable long catId) {
        if (catService.getCatById(catId) == null)
        {
            throw new CatNotFoundException(catId);
        }
        catService.deleteCat(catId);
    }

    @GetMapping(value = "/makeFriend", params = {"id1", "id2"})
    public List<CatOOP> makeFriend(@RequestParam String id1, @RequestParam String id2){
        long longId1 = Long.valueOf(id1).longValue();
        long longId2 = Long.valueOf(id2).longValue();
        if (catService.getCatById(longId1) == null)
        {
            throw new CatNotFoundException(longId1);
        }
        if (catService.getCatById(longId2) == null)
        {
            throw new CatNotFoundException(longId2);
        }
        CatOOP cat1 = catService.getCatById(longId1);
        CatOOP cat2 = catService.getCatById(longId2);

        catService.makeFriendWithCat(cat1, cat2);

        cat1 = catService.getCatById(longId1);
        cat2 = catService.getCatById(longId2);

        List<CatOOP> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);

        return cats;
    }



    @GetMapping(value = "/hello")
    public String hello()
    {
        return "ok";
    }
}
