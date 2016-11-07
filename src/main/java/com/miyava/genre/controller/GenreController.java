package com.miyava.genre.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miyava.common.AbstractController;
import com.miyava.genre.model.Genre;
import com.miyava.genre.service.GenreDao;
import com.miyava.util.BreadCrumbs;
import com.miyava.util.MessageHelper;

@Controller
@RequestMapping( GenreController.BASE_URL )
public class GenreController
    extends AbstractController {

    public GenreController() {
        super( BASE_URL );
    }

    protected final static String BASE_URL = "/genre";

    @Autowired
    private GenreDao genreDao;

    @RequestMapping( method = RequestMethod.GET )
    public String dashboard( Model model ) {
        BreadCrumbs.set( model, "genre.page.list.breadcrumb" );
        return "genre/genre";
    }

    @RequestMapping( "/new" )
    public String createForm( Model model ) {
        BreadCrumbs.set( model, "genre", "genre.page.list.breadcrumb", "genre.page.create.breadcrumb" );
        model.addAttribute( "genre", new Genre() );
        return "genre/createForm";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public String createAction( @Valid Genre genre, Errors errors, RedirectAttributes ra, Model model ) {
        if ( errors.hasErrors() ) {
            return "genre/createForm";
        }

        Genre savedGenre = genreDao.doSave( genre, errors );
        if ( savedGenre != null ) {
            MessageHelper.addSuccessAttribute( ra, "common.message.success_create" );
            return "redirect:/genre/" + savedGenre.getId();
        }
        else {
            MessageHelper.addGlobalCreateErrorAttribute( model, errors );
            return "genre/createForm";
        }
    }

}
