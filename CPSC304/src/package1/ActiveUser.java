package package1;

public class ActiveUser {
    private boolean isCustomer;
    private boolean isSeller;
    private int user_id;

    private static final ActiveUser instance = new ActiveUser();

    private ActiveUser() {
        this.isCustomer = false;
        this.isSeller = false;
        this.user_id = 0;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static ActiveUser getActiveUser(){
        return instance;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }
}
