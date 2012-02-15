

package controllers;

// core dependencies
import java.util.List;

import com.google.gson.GsonBuilder;

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
	    render(profile, user, scenes, posticks);
	}
	
    //Create an empty Postick and return its Unique ID to Client to update the contents
	public static void createPostick(){
			String author = params.get("author");
		 	Long sceneId = params.get("sceneId",Long.class);
	        Scene scene = Scene.findById(sceneId);
	        notFoundIfNull(scene);
	        //store random id
	        Postick nPostick = scene.createPostick(author);
	        String rId = nPostick.randomId;
	        String timestamp = nPostick.postedAt.toString();
	        renderJSON("{\"eventType\":\"creation_postit\", \"payload\":{ \"id\" : "+rId+" ,\"message\": \"200\"}, \"timestamp\":"+timestamp+",\"origin\":\"postit-service\" }");
	        //renderJSON(new StatusMessage(nPostick.randomId, nPostick.postedAt));
	    }
	
	public static void createPosticksJSON(Long id) {
		//renderJSON(new StatusMessage(id, "create_postit"));
	}
	
	//Update the Postick Contents by JSON
	public static void updatePostick(){
		//MyObject myObject = new GsonBuilder().create().fromJson(params.get("saveIt"), MyObject.class);
		Postick myPostick = new GsonBuilder().create().fromJson(params.get("saveIt"), Postick.class);

			//Get data from params
			String randomId			= myPostick.randomId;
	        float postickLeftPos 	= myPostick.postickLeftPos;
	        float postickTopPos 	= myPostick.postickTopPos;
	        String content			= myPostick.content;
	        //Query for the distinct postick
	        Postick newPostick = Postick.find("select distinct p from Postick p where p.randomId=?", randomId).first() ;
	        notFoundIfNull(newPostick);
		    newPostick.content = content;
		    newPostick.postickLeftPos = postickLeftPos;
		    newPostick.postickTopPos = postickTopPos;
		    newPostick.save();
	    }

//	public static void updatePostick(){
//		//Get data from params
//		String randomId			= params.get("randomId");
//        float postickLeftPos 	= params.get("leftPos", Float.class);
//        float postickTopPos 	= params.get("topPos", Float.class);
//        String content			= params.get("content");
//        //Query for the distinct postick
//        Postick newPostick = Postick.find("select distinct p from Postick p where p.randomId=?", randomId).first() ;
//        notFoundIfNull(newPostick);
//	    newPostick.content = content;
//	    newPostick.postickLeftPos = postickLeftPos;
//	    newPostick.postickTopPos = postickTopPos;
//	    newPostick.save();
//    }
	

	//Delete a postick directly by UID = Unique Random Id
	public static void deletePostick(){
		String randomId = params.get("randomId");
		Postick.delete("from Postick p where p.randomId=?", randomId);
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