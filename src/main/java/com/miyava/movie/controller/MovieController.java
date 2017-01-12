package com.miyava.movie.controller;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miyava.common.AbstractController;
import com.miyava.movie.model.Movie;
import com.miyava.movie.service.MovieDao;
import com.miyava.util.AjaxUtils;
import com.miyava.util.BreadCrumbs;
import com.miyava.util.MessageHelper;

@JsonIgnoreProperties(ignoreUnknown = true)
@Controller
@RequestMapping( MovieController.BASE_URL )
public class MovieController
    extends AbstractController {

    protected final static String BASE_URL = "/movie";

    protected final static String CREATE_FORM = "movie/createForm";

    private static final String MOVIE_VIEW_NAME = "movie/editForm";

    private static final String MOVIE_TAB_VIEW_NAME = "movie/movietabs";

    @Autowired
    private MovieDao movieDao;

    public MovieController() {
        super( BASE_URL );
    }

    @RequestMapping( method = RequestMethod.GET )
    public String dashboard( Model model ) {
        BreadCrumbs.set( model, "movie.page.list.breadcrumb" );
        return "movie/movie";
    }
    
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String show( @PathVariable Long id, Model model ) { 
        model.addAttribute( "movie", movieDao.findOne( id ) );
        return MOVIE_VIEW_NAME;
    }
    
    @RequestMapping( value = "/{id}/maintab", method = RequestMethod.GET )
    public String mainTab( @PathVariable Long id, Model model,
                           @RequestHeader( value = "X-Requested-With", required = false ) String requestedWith ) {

       model.addAttribute( "movie", movieDao.findOne( id ) );

        if ( AjaxUtils.isAjaxRequest( requestedWith ) ) {
            return MOVIE_TAB_VIEW_NAME.concat( " :: maintab" );
        }
        else {
            return MOVIE_VIEW_NAME;
        }
    }

    @RequestMapping( "/new" )
    public String createForm( Model model ) {
        BreadCrumbs.set( model, "movie", "movie.page.list.breadcrumb", "movie.page.create.breadcrumb" );
        model.addAttribute( "movie", new Movie() );
        return CREATE_FORM;
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public String createAction( @Valid Movie movie, Errors errors, RedirectAttributes ra, Model model ) {
        if ( errors.hasErrors() ) {
            return CREATE_FORM;
        }

        Movie savedMovie = movieDao.doSave( movie, errors );
        if ( savedMovie != null ) {
            MessageHelper.addSuccessAttribute( ra, "common.message.success_create" );
            return "redirect:/movie/" + savedMovie.getId();
        }
        else {
            MessageHelper.addGlobalCreateErrorAttribute( model, errors );
            return CREATE_FORM;
        }
    }

    @RequestMapping( value = "/list", method = RequestMethod.GET )
    public String listMovies( @Valid Movie movie, Errors errors, RedirectAttributes ra, Model model )
        throws IOException {
        List<String> liste = getList(ra);
        model.addAttribute( "lists", liste );
        return "movie/list";
    }

    private List<String> getList(RedirectAttributes ra){
        URL url = null;
        Movie movie = null;
        
        List<String> list = new ArrayList<String>();
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Errors errors = null;
        
        try {
            url = new URL("https://api.themoviedb.org/3/movie/880?api_key=e32412fcd041dff3927fd5c7c5498600&language=de");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                String jsonText = readAll(reader);
                movie = jsonMapper.readValue(jsonText, Movie.class);
                if(movie != null){
                    Movie savedMovie = movieDao.doSave( movie, errors );
                    if ( savedMovie != null ) {
                        //MessageHelper.addSuccessAttribute( ra, "common.message.success_create" );
                    }
                    else {
                        //MessageHelper.addErrorAttribute( ra, "nope" );
                    }                    
                }
                list.add( movie.getTitle() );
            } catch (ConstraintViolationException e) {
                // Ignore the exception here by doing nothing
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch ( MalformedURLException e ) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }
}
