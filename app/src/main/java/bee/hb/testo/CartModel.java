package bee.hb.testo;

/**
 * Created by biniam on 10/2/2018.
 */
public class CartModel {
    String title;
    String description;
    String cost;
    String count;
    String id;
    int icon;

    //constructor
    public CartModel(String title, String desc, int icon, String cost, String count, String id) {
        this.title = title;
        this.description = desc;
        this.icon = icon;
        this.cost = cost;
        this.count = count;
        this.setId(id);
    }

    //getters and setters

    public String getTitle() {
        return this.title;
    }

    public void setTitle() {
         this.title = title;
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

    public String getCount() {
        return this.count;
    }
    public void setCount() {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
