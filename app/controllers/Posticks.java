package controllers;

import java.util.List;

import models.Postick;
import models.Scene;
import play.*;
import play.mvc.*;

@Check("admin")
@With(Secure.class)
public class Posticks extends CRUD {    
	
	public static void index(Long sceneId) {
		
		Scene findById = Scene.findById(sceneId);
		List<Postick> posticks = findById.posticks;
		render(posticks);
	}
}


/*
 public static void showTweets(String username) {
 User user = User.find("byLogin", username).first(); 
 notFoundIfNull(user); 
 List<Tweet> tweets = Tweet.find("user = ? order by postedAt DESC", user).fetch(20); 
 render(tweets, user);
}
 */