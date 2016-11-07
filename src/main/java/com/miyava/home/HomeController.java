package com.miyava.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // Dashboard
    @RequestMapping( "/" )
    public String dashboard( Model model ) {
        return "home/dashboard";
    }

    @RequestMapping( "/login" )
    public String login( Model model ) {
        return "home/login";
    }

    // // Login form
    // @RequestMapping( "/login" )
    // public String login( @RequestParam( required = false ) String error, @RequestParam( required = false ) String
    // logout, Model model ) {
    // // if ( error != null ) {
    // // MessageHelper.addErrorAttribute( model, "login.messages.login_error" );
    // // }
    // // else if ( logout != null ) {
    // // MessageHelper.addErrorAttribute( model, "login.messages.logout_success" );
    // // }
    //
    // return "home/login";
    // }

}
