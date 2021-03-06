package ee.bcs.valiit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static ee.bcs.valiit.services.OmniMeterService.getStatsByUuid;
import static ee.bcs.valiit.services.OmniMeterService.getUser;

public class Meeting {

    private int id;
    private int meetingOwnerId;
    private String subject;
    private String uniqueHash = UUID.randomUUID().toString();
    private String type;
    private String date;
    private String time;
    private String details;
    private User meetingHolder;
    private Stats meetingStats;

    public Meeting() {
    }

    public User getMeetingHolder() {
        return meetingHolder;
    }

    public void setMeetingHolder(User meetingHolder) {
        this.meetingHolder = meetingHolder;
    }

//    private List<String> listOfParticipants;

    public Meeting(ResultSet result) {
        try {
            this.setMeetingId(result.getInt("id"));
            this.setOwnerId(result.getInt("meeting_owner_id"));
            this.setType(result.getString("type"));
            this.setDate(result.getString("date"));
            this.setTime(result.getString("time"));
            this.setSubject(result.getString("subject"));
            this.setDetails(result.getString("details"));
            this.setUniqueHash(result.getString("uuid"));
            this.setMeetingHolder(getUser(this.getOwnerId()));
            this.setMeetingStats(getStatsByUuid(this.getUniqueHash()));

            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Stats getMeetingStats() {
        return meetingStats;
    }

    public void setMeetingStats(Stats meetingStats) {
        this.meetingStats = meetingStats;
    }
    public String getUniqueHash () {
        return uniqueHash;
    }

    public void setUniqueHash (String uniqueHash) {
        this.uniqueHash = uniqueHash;
    }

    public Meeting(int id, int meetingOwnerId, String type, String subject, String date, String time, String details, User meetingHolder ) {
        this.id = id;
        this.meetingOwnerId = meetingOwnerId;
        this.type = type;
        this.subject = subject;
        this.details = details;
        this.date = date;
        this.time = time;
        this.meetingHolder = meetingHolder;
    }
    public int getMeetingId() {
        return id;
    }

    public void setMeetingId(int meetingId) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setType (String type) {this.type = type;    }

    public String getType() { return type;  }

    public String getDetails() { return details;  }

    public void setDetails (String details) {this.details = details;    }

    public String getDate() { return date;   }

    public void setDate(String date) { this.date = date;    }

    public String getTime() { return time;   }

    public void setTime (String time) {
        this.time = time;
    }

    public int getOwnerId() {
        return meetingOwnerId;
    }

    public void setOwnerId(int meetingOwnerId) {this.meetingOwnerId = meetingOwnerId;    }

//    public List<String> getListOfParticipants() {
//        return listOfParticipants;
//    }

//    public void setListOfParticipants(List<String> listOfParticipants) {
//        this.listOfParticipants = listOfParticipants;
//    }


}

