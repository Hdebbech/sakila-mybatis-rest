package com.debbech.sakila.rest.controllers;

import com.debbech.sakila.repositories.mybatis.FilmMapper;
import com.debbech.sakila.rest.dto.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

    @Autowired
    private FilmMapper filmMapper;

    @GetMapping("/films")
    public List<Film> findByCategory(@RequestParam(value = "category", required = false) String category){
        return filmMapper.findByCategory(category);
    }

    @GetMapping("/films/{filmId}")
    public Film findById(@PathVariable("filmId") Long filmId){
        return filmMapper.findById(filmId);
    }
}
