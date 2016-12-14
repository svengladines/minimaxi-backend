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
	String mediaType;
	String momentDay;
	String momentMonth;
	String momentYear;
	
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

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public String getMomentDay() {
		return momentDay;
	}

	public void setMomentDay(String momentDay) {
		this.momentDay = momentDay;
	}

	public String getMomentMonth() {
		return momentMonth;
	}

	public void setMomentMonth(String momentMonth) {
		this.momentMonth = momentMonth;
	}

	public String getMomentYear() {
		return momentYear;
	}

	public void setMomentYear(String momentYear) {
		this.momentYear = momentYear;
	}

	@Override
	public String toString() {
		
		return this.title;
		
	}

}
