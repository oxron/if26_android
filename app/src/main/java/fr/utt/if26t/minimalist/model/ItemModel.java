package fr.utt.if26t.minimalist.model;

public class ItemModel {
    private Long id;
    private String name;
    private String note;
    private int important;
    private int planifie;
    private String date;
    private int done;
    private int list;

    public ItemModel(Long id, String name, String note, int important, int planifie, String date, int done, int list) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.important = important;
        this.planifie = planifie;
        this.date = date;
        this.done = done;
        this.list = list;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", important=" + important +
                ", planifie=" + planifie +
                ", date='" + date + '\'' +
                ", done=" + done +
                ", list=" + list +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public int getPlanifie() {
        return planifie;
    }

    public void setPlanifie(int planifie) {
        this.planifie = planifie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getList() {
        return list;
    }

    public void setList(int list) {
        this.list = list;
    }
}
