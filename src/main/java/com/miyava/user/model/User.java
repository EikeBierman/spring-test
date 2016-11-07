package com.miyava.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;
import com.miyava.auditing.AuditedEntityWithUUID;
import com.miyava.common.NotEmpty;

@Entity
public class User
    extends AuditedEntityWithUUID {

    @NotEmpty( message = "user.messages.username_empty" )
    @Email( message = "user.messages.username_wrong_format" )
    @Column( nullable = false, unique = true, length = 100 )
    @Length( max = 100, message = "common.message.data_to_long" )
    @JsonView( DataTablesOutput.View.class )
    private String email;

    @NotEmpty( message = "user.messages.username_empty" )
    @Column( nullable = false, unique = true, length = 100 )
    @Length( max = 100, message = "common.message.data_to_long" )
    @JsonView( DataTablesOutput.View.class )
    private String username;

    @Column( nullable = false, length = 255 )
    @Length( max = 255, message = "common.message.data_to_long" )
    private String password;

    @Transient
    private String passwordOld;

    @Transient
    private String passwordConfirm;

    @JsonView( DataTablesOutput.View.class )
    private boolean enabled;

    @NotEmpty( message = "user.messages.firstname_empty" )
    @Column( length = 100 )
    @Length( max = 100, message = "common.message.data_to_long" )
    @JsonView( DataTablesOutput.View.class )
    private String firstname;

    @NotEmpty( message = "user.messages.lastname_empty" )
    @Column( length = 100 )
    @Length( max = 100, message = "common.message.data_to_long" )
    @JsonView( DataTablesOutput.View.class )
    private String lastname;

    // @NotEmpty( message = "user.messages.birthday" )
    // private Date birthday;

    @ElementCollection( fetch = FetchType.EAGER )
    @JoinTable( name = "user_roles", joinColumns = @JoinColumn( name = "id" ) )
    @Column( nullable = false )
    @Enumerated( EnumType.STRING )
    @JsonView( DataTablesOutput.View.class )
    private Set<Role> userRoles;

    public User( String id ) {
        super( id );
    }

    public User() {
        // needed for serialization
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname( String firstname ) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname( String lastname ) {
        this.lastname = lastname;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm( String passwordConfirm ) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getUserRoles() {
        if ( userRoles == null ) {
            userRoles = new HashSet<>();
        }
        return userRoles;
    }

    public void setUserRoles( Set<Role> userRoles ) {
        this.userRoles = userRoles;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld( String passwordOld ) {
        this.passwordOld = passwordOld;
    }

}
