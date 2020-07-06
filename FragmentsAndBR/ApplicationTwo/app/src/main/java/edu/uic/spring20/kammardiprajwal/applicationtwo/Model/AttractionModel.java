package edu.uic.spring20.kammardiprajwal.applicationtwo.Model;

public class AttractionModel {
    String name;
    String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "AttractionModel{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
