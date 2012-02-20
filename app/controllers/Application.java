

package controllers;

// core dependencies
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import flexjson.JSONSerializer;

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
	    render(profile, user, scenes);
	}
	
	     public static void getJSON(JsonObject json){
	    	 Postick postick = new Postick();
	    	 //Scene.all().first();
	    	 JSONSerializer modelSerializer = new JSONSerializer().include("author","scene").exclude("*");
	    	 renderJSON(modelSerializer.serialize(postick));
	    	 renderText(postick);
//	     FirstModel model = FirstModel.all().fetch();
//	     JSONSerializer modelSerializer = new JSONSerializer().include("company","secondmodel.id","secondmodel.test").exclude("*");
	     //renderJSON(modelSerializer.serialize(postick));
	    }
	
    //Create an empty Postick and return its Unique ID to Client to update the contents
	public static void createPostick(){
		
			String author = params.get("author");
		 	Long sceneId = params.get("sceneId",Long.class);
	        Scene scene = Scene.findById(sceneId);
	        notFoundIfNull(scene);
	        //store random id
	        Postick postick = scene.createPostick(author);
	        renderTemplate("Posticks/newPostick.json", postick);
	    }
	
	
	public static void createJSONPostick(JsonObject json){ 
		renderJSON(json);
		//JsonElement jsonElement = json.get("author");
		//JsonElement jsonElement2 = json.get("sceneId");
		//System.out.println(jsonElement);
		renderText(json);
	}
	
	
	
	//Update the Postick Contents by JSON
	public static void updatePostick(JsonObject jso){
		//MyObject myObject = new GsonBuilder().create().fromJson(params.get("saveIt"), MyObject.class);
		Postick myPostick = new GsonBuilder().create().fromJson(params.get("saveIt"), Postick.class);
		//Postick myPostick = new Gson().fromJson(jso, Postick.class);

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
		    renderTemplate("Posticks/status.json");
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
		renderTemplate("Posticks/status.json");
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
	
	//JSON TESTING
    public static void index2() {
        render();
    }
    


    public static void addBar(Bar bar) {
        bar.save();
        index2();
    }
    
    public static void listBars() {
        renderJSON(Bar.findAll());
    }
    
    
    public static void index3() {
        render();
    }
    public static void addPostick(Postick postick) {
    	postick.save();
        index3();
    }
    
    public static void listPosticks() {
        renderJSON(Postick.findAll());
    }
}