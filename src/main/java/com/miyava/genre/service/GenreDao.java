package com.miyava.genre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.miyava.common.CrudDao;
import com.miyava.genre.model.Genre;
import com.miyava.genre.repository.GenreRepository;

@Service
public class GenreDao extends CrudDao<Genre, Long, GenreRepository> {

    @Autowired
    public GenreDao( GenreRepository repository ) {
        super( repository );
    }

    @Override
    public boolean validateOnSaveOrUpdate( Optional<Genre> oldEntity, Genre entity, Errors errors ) {
        if ( entity == null ) {
            return false;
        }

        if ( Strings.isNullOrEmpty( entity.getGenre() ) ) {
            errors.rejectValue( "genre", "genre.messages.genre_empty" );
            return false;
        }

        // Genre bereits vergeben?
        Genre tmpGenre = findOneByGenre( entity.getGenre() );
        if ( tmpGenre != null && !tmpGenre.getId().equals( entity.getId() ) ) {
            errors.rejectValue( "genre", "genre.messages.id_already_exists", new Object[] { entity.getGenre() }, null );
            return false;
        }

        return true;
    }

    @Override
    public boolean validateOnDelete( Genre entity, Errors errors ) {
        if ( entity != null ) {
            return true;
        }
        return false;
    }

    public Genre findOneByGenre( String genre ) {
        return repository.findOneByGenre( genre );
    }

}
