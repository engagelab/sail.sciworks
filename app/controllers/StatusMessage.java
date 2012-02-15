package controllers;

import java.util.Date;

public class StatusMessage {
		public String id;
		public String eventType;
		public Date timestamp;

	   public StatusMessage(String passedId, Date date) {
		   this.eventType = "creation_postit";
		   this.id = passedId.toString();
		   this.timestamp = date;
	   }
}
