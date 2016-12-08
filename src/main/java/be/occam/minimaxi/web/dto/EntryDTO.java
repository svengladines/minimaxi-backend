package be.occam.minimaxi.web.dto;

import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;

import be.occam.minimaxi.domain.object.Entry;
import be.occam.utils.timing.Timing;

public class EntryDTO {
	
	String title;
	String description;
	List<String> recipients;
	MediaType mediaType;
	Date moment;
	Date posted;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	@Override
	public String toString() {
		
		return this.title;
		
	}

}
