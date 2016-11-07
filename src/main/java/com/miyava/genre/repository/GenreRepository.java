package com.miyava.genre.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.miyava.genre.model.Genre;

public interface GenreRepository
    extends DataTablesRepository<Genre, Long> {

    Genre findOneByGenre( String genre );
}
