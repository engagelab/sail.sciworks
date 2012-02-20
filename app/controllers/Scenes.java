package controllers;

import java.util.List;

import models.*;

import play.*;
import play.db.jpa.JPABase;
import play.libs.Codec;
import play.mvc.*;

public class Scenes extends CRUD { 
	//Render Scene by ID
    public static void renderScene(Long sceneId) {
        Scene scene = Scene.findById(sceneId);
        List<Postick> posticks = scene.posticks;
        renderTemplate("render/scene.json", posticks);
    }
}