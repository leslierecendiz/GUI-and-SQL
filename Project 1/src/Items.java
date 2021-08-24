
public class Items {
	//setters and getter for inventory
    private double price;
    private String ItemsID;
    private String name;
    private String active;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemsID() {
        return ItemsID;
    }

    public void setItemsID(String ItemsID) {
        this.ItemsID = ItemsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String isActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}