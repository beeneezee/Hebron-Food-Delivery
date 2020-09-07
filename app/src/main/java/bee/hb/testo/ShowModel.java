package bee.hb.testo;

/**
 * Created by biniam on 10/2/2018.
 */
public class ShowModel {
    String title;
    String description;
    String cost;
    String type;
    String extra;
    private String id;
    int icon;

    //constructor
    public ShowModel(String title, String desc, int icon, String cost, String type, String id, String extra) {
        this.title = title;
        this.description = desc;
        this.icon = icon;
        this.cost = cost;
        this.type = type;
        this.extra = extra;
        this.setId(id);
    }

    //getters and setters

    public String getTitle() {
        return this.title;
    }

    public void setTitle() {
        this.title = title;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra() {
        this.extra = extra;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon() {
        this.icon = icon;
    }

    public String getCost() {
        return this.cost;
    }

    public void setCost() {
        this.cost = cost;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription() {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType() {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
