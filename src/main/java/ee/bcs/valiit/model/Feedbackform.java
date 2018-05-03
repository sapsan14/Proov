package ee.bcs.valiit.model;

import java.util.Date;

public class Feedbackform {

    private int meetingId;
    private String meetingSubject;
    private Date meetingDateTime;
    private String meetingOrganizer;
    private int feedBackAsNumber;
    private String comment;


    public Feedbackform() {

    }

    public Feedbackform(int meetingId, String meetingSubject, Date meetingDateTime, String meetingOrganizer, int feedBackAsNumber, String comment) {
        this.meetingId = meetingId;
        this.meetingSubject = meetingSubject;
        this.meetingDateTime = meetingDateTime;
        this.meetingOrganizer = meetingOrganizer;
        this.feedBackAsNumber = feedBackAsNumber;
        this.comment = comment;
    }


    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public Date getMeetingDateTime() {
        return meetingDateTime;
    }

    public void setMeetingDateTime(Date meetingDateTime) {
        this.meetingDateTime = meetingDateTime;
    }

    public String getMeetingOrganizer() {
        return meetingOrganizer;
    }

    public void setMeetingOrganizer(String meetingOrganizer) {
        this.meetingOrganizer = meetingOrganizer;
    }

    public int getFeedBackAsNumber() {
        return feedBackAsNumber;
    }

    public void setFeedBackAsNumber(int feedBackAsNumber) {
        this.feedBackAsNumber = feedBackAsNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
