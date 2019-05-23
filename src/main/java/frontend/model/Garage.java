package frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Garage {

    private int id;

    private int id_partner;

    private String partner;

    private String name;

    private String phone;

    private String description;

    private Address address;

    private ArrayList<String> coordinates;

    private ArrayList<Comment> comments;

    public Garage() {
    }

    @JsonIgnore
    public int getNumberComment() {
        return getComments().size();
    }

    @JsonIgnore
    public int getAverageNote() {
        int average = 0;
        for (Comment comment: getComments()) {
            average += comment.getNote();
        }

        return comments.size() != 0 ? average / comments.size() : 0;
    }

    @JsonIgnore
    public int getNumberNote(int note) {
        int number = 0;
        for (Comment comment: getComments()) {
            if(comment.getNote() == note)
                number++;
        }
        return number;
    }

    @JsonIgnore
    public int getPercentNote(int note) {
        if(getComments().size() == 0 || getNumberNote(note) == 0) {
            return 0;
        }
        return Math.round((float) getNumberNote(note) / getComments().size() * 100);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_partner() {
        return id_partner;
    }

    public void setId_partner(int id_partner) {
        this.id_partner = id_partner;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<String> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "id=" + id +
                ", id_partner=" + id_partner +
                ", partner='" + partner + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", coordinates=" + coordinates +
                ", comments=" + comments +
                '}';
    }
}


