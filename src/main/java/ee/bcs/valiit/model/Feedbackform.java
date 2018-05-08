package ee.bcs.valiit.model;

import java.util.Date;

public class Feedbackform {

    private int feedBackFormId;
    private int meetingId;
    private String meetingSubject;
    private Date meetingDateTime;
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

    public Feedbackform(int feedBackFormId, int meetingId, String meetingSubject, Date meetingDateTime, String meetingOrganizer, int feedBackAsNumber, String comment) {
        this.feedBackFormId = getFeedBackFormId();
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

    public static int addNumber(String str1, String str2) {
        int a = Integer.parseInt(str1);
        int b = Integer.parseInt(str2);

        int sum = a + b;
        return sum;
    }



}
