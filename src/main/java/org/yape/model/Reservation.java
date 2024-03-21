package org.yape.model;

public class Reservation {
    private String startDate;
    private String endDate;
    private String destination;
    private int numChild;
    private String[] ageChildren;
    private int numAdults;
    private int numRooms;
    private int itemNumber;
    private String purpose;

    public Reservation(String startDate, String endDate, String destination, int numChild, String[] ageChildren, int numAdults, int numRooms, int itemNumber, String purpose) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.numChild = numChild;
        this.ageChildren = ageChildren;
        this.numAdults = numAdults;
        this.numRooms = numRooms;
        this.itemNumber = itemNumber;
        this.purpose = purpose;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public int getNumChild() {
        return numChild;
    }
    public void setNumChild(int numChild) {
        this.numChild = numChild;
    }
    public String[] getAgeChildren() {
        return ageChildren;
    }
    public void setAgeChildren(String[] ageChildren) {
        this.ageChildren = ageChildren;
    }
    public int getNumAdults() {
        return numAdults;
    }
    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }
    public int getNumRooms() {
        return numRooms;
    }
    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }
    public int getItemNumber() {
        return itemNumber;
    }
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
}
