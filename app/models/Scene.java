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
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
    
    //Create Empty Postick and return SceneID
    public Long createEmptyPostick(String author) {
    	
    	//create defaut parameters for the empty postick
    	String content = "";
    	int postickLeftPos = 0;
    	int postickTopPos = 0;
    	
        Postick newPostick = new Postick(this, author, content, postickLeftPos, postickTopPos);
        this.posticks.add(newPostick);
        this.save();
        //Return the Postick index
        return newPostick.id;
    }
    
    //Update Postick by ID
    public Scene updatePostick(Long postickId, String content, int postickLeftPos, int postickTopPos) {
        Postick newPostick = Postick.findById(postickId);
        newPostick.content = content;
        newPostick.postickLeftPos = postickLeftPos;
        newPostick.postickTopPos = postickTopPos;
        
        //this.posticks.add(ID, newPostick);
        //this.posticks.add(newPostick);
        //update the scene with new postick
        this.save();
        return this;
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
