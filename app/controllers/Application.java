

package controllers;

// core dependencies
import java.util.List;

import play.i18n.Lang;
import play.mvc.Controller;
import play.mvc.With;

// other Dependencies
import models.*;
import play.*;
import play.libs.*;
import play.cache.*;
import play.data.validation.*;
import play.db.jpa.JPABase;


@With(Secure.class)
public class Application extends Controller {

	/**
	 * Render LogIN and User Profile Page
	 */
	public static void index() {
		
		Profile profile = Profiles.getCurrentProfile();
		String username = Security.connected();	    
	    User user = User.findUserByUsername(username);
	    
	    List<Scene> scenes = Scene.findAll();
	    Scene scene = scenes.get(0);
	    List<Postick> posticks = scene.posticks;
	    render(profile, user, scenes,posticks);
	    render(profile, user, scenes, posticks);
	}
	
    //Create an empty Postick and return its Unique ID to Client to update the contents
	public static Long createEmptyPostick(){
			String author = params.get("author");
		 	Long sceneId = params.get("sceneId",Long.class);
	        Scene scene = Scene.findById(sceneId);
	        return scene.createEmptyPostick(author);
	    }
	
	//Update the Postick Contents
	public static void updatePostick(){
			//Get data from params
	        Long sceneId 			= params.get("sceneId",Long.class);
	        Long postickId			= params.get("postickId",Long.class);
	        float postickLeftPos 	= params.get("leftPos", Float.class);
	        float postickTopPos 	= params.get("topPos", Float.class);
	        String author 			= params.get("author");
	        String content			= params.get("content");
	        
	        //Find respective Scene
	        Scene scene = Scene.findById(sceneId);
	        //Update respecitve Postick with aquired data
	        scene.updatePostick(author, content, postickLeftPos, postickTopPos);
	        //scene.updatePostick(author, content, postickLeftPos, postickTopPos);
	        flash.success("Thanks for posting %s", author);
	       // show(sceneId);
	    }

	public static void changeLanguage(String lang) {
		Lang.change(lang);
		System.out.println("the language is now " + lang);
		try {
			Secure.login();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}