package com.miyava.genre.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.miyava.genre.controller.GenreController;
import com.miyava.genre.controller.GenreRestController;
import com.miyava.genre.model.Genre;
import com.miyava.genre.service.GenreDao;

@RestController
@RequestMapping( GenreRestController.BASE_URL )
public class GenreRestController {

    protected final static String BASE_URL = GenreController.BASE_URL + "/data";

    @Autowired
    private GenreDao genreDao;

    @JsonView( DataTablesOutput.View.class )
    @RequestMapping( value = "/genres", method = RequestMethod.GET )
    public DataTablesOutput<Genre> getGenres( @Valid DataTablesInput input ) {
        return genreDao.findAll( input );
    }

}
