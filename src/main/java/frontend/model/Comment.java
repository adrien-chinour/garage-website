package frontend.model;

public class Comment {

    private int id;

    private int client_id;

    private String comment;

    private int note;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", comment='" + comment + '\'' +
                ", note=" + note +
                '}';
    }
}
