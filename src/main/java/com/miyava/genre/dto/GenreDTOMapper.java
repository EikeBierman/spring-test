package com.miyava.genre.dto;

import com.miyava.common.EntityToDTOMapper;
import com.miyava.genre.model.Genre;

public class GenreDTOMapper
    implements EntityToDTOMapper<Genre, GenreDTO> {

    @Override
    public GenreDTO apply( Genre input ) {
        GenreDTO dto = new GenreDTO();
        dto.setId(input.getId());
        dto.setGenre(input.getGenre());

        return dto;
    }

}
