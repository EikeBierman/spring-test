package com.miyava.genre.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;
import com.miyava.auditing.AuditedEntity;
import com.miyava.common.NotEmpty;

@Entity
public class Genre
    extends AuditedEntity
    implements com.miyava.common.Entity<Long> {

    @Column( name = "genre_type_id" )
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @JsonView( DataTablesOutput.View.class )
    private Long id;

    @NotEmpty( message = "genre.messages.genre_empty" )
    @Column( nullable = false, unique = true, length = 100 )
    @Length( max = 100, message = "common.message.data_to_long" )
    @JsonView( DataTablesOutput.View.class )
    private String genre;

    public Genre() {}

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre( String genre ) {
        this.genre = genre;
    }

}
