package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
@CompoundIndex(name = "user_type_index", def = "{'user_type': 1}")
public class User {

    @Id
    private String id;

    @Field("user_type")
    private String userType;

    @Indexed(unique = true)
    private String username;

    @Field
    private String password;

    @Field
    private String name;

    @Indexed(unique = true)
    private String email;

    @Field
    private Date birthdate;

    @Field("phone_number")
    private String phoneNumber;

    @Field
    private boolean active;

    @DBRef(lazy = true)
    private List<Purchase> purchaseList;


    public User(){ }

    public User(String username, String password, String name, String email, Date birthdate, String phoneNumber) {
        this.setActive(true);
        this.setUsername(username);
        this.setPassword(password);
        this.setName(name);
        this.setEmail(email);
        this.setBirthdate(birthdate);
        this.setPhoneNumber(phoneNumber);
        this.setPurchaseList(new ArrayList<Purchase>());
        this.setUserType(this.getClass().getSimpleName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (this.getId() != null && this.getUsername() != null) {
            return ;
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeletable(){
        return (this.getPurchaseList().isEmpty() && this.isActive());
    }

    public boolean isBaneable(){
        return (!this.getPurchaseList().isEmpty());
    }

    public void addPurchase(Purchase purchase){
        if(!this.getPurchaseList().contains(purchase)){
            this.getPurchaseList().add(purchase);
        }
    }
}
