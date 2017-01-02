package be.occam.minimaxi.web.dto;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonProperty;

public class AdventureDTO {
	
	  protected String media;
	  protected String type;
	  protected String title;
	  protected String description;
	  protected String date;
	  protected String mediaURL;
	  protected Integer visited;
	  protected String uuid;
	  
	  public String getMedia() {
		return media;
	  }
	  
	  public void setMedia(String media) {
		this.media = media;
	  }
	
	  public String getType() {
		return type;
	  }
	
	public void setType(String type) {
		this.type = type;
	}
	
	@JsonProperty
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonProperty
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	@JsonProperty
	public String getMediaURL() {
		return mediaURL;
	}
	
	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	public Integer getVisited() {
		return visited;
	}

	public void setVisited(Integer visited) {
		this.visited = visited;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
