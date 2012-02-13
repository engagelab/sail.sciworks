package models;
 
import java.util.*;
import javax.persistence.*;

import play.data.binding.*;
import play.data.validation.*;
import play.db.jpa.Model;
 
@Entity
public class Postick extends Model {
	
	@Required
    public int postickLeftPos;
	
	@Required
    public int postickTopPos;
	
 
    @Required
    public String author;
    
    @Required
    public Date postedAt;
     
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @ManyToOne
    @Required
    public Scene scene;
    
    public Postick(Scene scene, String author, String content, int postickLeftPos, int postickTopPos ) {
        this.scene = scene;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
        this.postickLeftPos = postickLeftPos;
        this.postickTopPos = postickTopPos;
        
    }
    

    
    
    public String toString() {
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }
 
}