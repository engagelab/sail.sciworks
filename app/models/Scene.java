package models;
 
import java.util.*;
import javax.persistence.*;

import play.data.binding.*;
import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class Scene extends Model {
 
    @Required
    public String title;
    
    @Required @As("yyyy-MM-dd")
    public Date postedAt;
    
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @Required
    @ManyToOne
    public User author;
    
    @OneToMany(mappedBy="scene", cascade=CascadeType.ALL)
    public List<Postick> posticks;
    
    public Scene(User author, String title, String content) { 
        this.posticks = new ArrayList<Postick>();
        //Author and User
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
    
    //Create Empty Postick and return SceneID
    public Postick createPostick(String author) {
    	
    	//create defaut parameters for the empty postick
    	String content = "";
    	float postickLeftPos = 0.0f;
    	float postickTopPos = 0.0f;
    	
        Postick newPostick = new Postick(this, author, content, postickLeftPos, postickTopPos);
        this.posticks.add(newPostick);
        this.save();
        return newPostick;
    }
    
    public Scene previous() {
        return Scene.find("postedAt < ? order by postedAt desc", postedAt).first();
    }

    public Scene next() {
        return Scene.find("postedAt > ? order by postedAt asc", postedAt).first();
    }
    
    public String toString() {
        return title;
    }
 
}
