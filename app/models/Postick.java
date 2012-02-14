package models;
 
import java.util.*;
import javax.persistence.*;

import play.data.binding.*;
import play.data.validation.*;
import play.db.jpa.Model;
import play.libs.Codec;
 
@Entity
public class Postick extends Model {
	
	@Required
    public float postickLeftPos;
	
	@Required
    public float postickTopPos;
	
	@Required
	public String randomId;
	
 
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
    
    public Postick(Scene scene, String author, String content, float postickLeftPos, float postickTopPos ) {
        this.scene = scene;
        this.author = author;
        this.content = content;
        this.randomId = Codec.UUID();
        this.postedAt = new Date();
        this.postickLeftPos = postickLeftPos;
        this.postickTopPos = postickTopPos;
        
    }
    

    
    
    public String toString() {
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }
 
}