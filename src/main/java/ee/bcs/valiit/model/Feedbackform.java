package ee.bcs.valiit.model;

import java.util.Date;

public class Feedbackform {


    private int feedBackFormId;
    private String meetingUuid;
    private String meetingSubject;
    private String meetingDate;
    private String meetingTime;
    private String meetingOrganizer;
    private int feedBackAsNumber;
    private String comment;


    public Feedbackform() {

    }
    public int getFeedBackFormId() {
        return feedBackFormId;
    }

    public void setFeedBackFormId(int feedBackFormId) {
        this.feedBackFormId = feedBackFormId;
    }

    public Feedbackform(int feedBackFormId, String meetingUuid, String meetingSubject, String meetingDate,String meetingTime, String meetingOrganizer, int feedBackAsNumber, String comment) {
        this.feedBackFormId = getFeedBackFormId();
        this.meetingUuid = meetingUuid;
        this.meetingSubject = meetingSubject;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingOrganizer = meetingOrganizer;
        this.feedBackAsNumber = feedBackAsNumber;
        this.comment = comment;
    }


    public String getMeetingUuid() {
        return meetingUuid;
    }

    public void setMeetingUuId(String meetingUuid) {
        this.meetingUuid = meetingUuid;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingUuid(String meetingUuid) {
        this.meetingUuid = meetingUuid;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
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
