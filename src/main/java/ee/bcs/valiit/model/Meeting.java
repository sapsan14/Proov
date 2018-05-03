package ee.bcs.valiit.model;

import java.util.Date;
import java.util.List;

public class Meeting {

    private int meetingOwnerId;
    private int meetingTypeId;
    private String subject;
    private Date dateTimeForMeeting;
    private String detailsOfMeeting;
    private List<String> listOfParticipants;

    public Meeting() {

    }

    public Meeting(int meetingOwnerId, int meetingTypeId, String subject, Date dateTimeForMeeting, String detailsOfMeeting, List listOfParticipants) {
        this.meetingOwnerId = meetingOwnerId;
        this.meetingTypeId = meetingTypeId;
        this.subject = subject;
        this.dateTimeForMeeting = dateTimeForMeeting;
        this.detailsOfMeeting = detailsOfMeeting;
        this.listOfParticipants = listOfParticipants;
    }

    public int getMeetingOwnerId() {
        return meetingOwnerId;
    }

    public void setMeetingOwnerId(int meetingOwnerId) {
        this.meetingOwnerId = meetingOwnerId;
    }

    public int getMeetingTypeId() {
        return meetingTypeId;
    }

    public void setMeetingTypeId(int meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateTimeForMeeting() {
        return dateTimeForMeeting;
    }

    public void setDateTimeForMeeting(Date dateTimeForMeeting) {
        this.dateTimeForMeeting = dateTimeForMeeting;
    }

    public String getDetailsOfMeeting() {
        return detailsOfMeeting;
    }

    public void setDetailsOfMeeting(String detailsOfMeeting) {
        this.detailsOfMeeting = detailsOfMeeting;
    }

    public List<String> getListOfParticipants() {
        return listOfParticipants;
    }

    public void setListOfParticipants(List<String> listOfParticipants) {
        this.listOfParticipants = listOfParticipants;
    }


}

