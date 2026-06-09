    package customers;

public class CustomerMethods {  
    private int id;
    private String name;
    private String phone;
    private String license;
    private String address;
    
    public CustomerMethods(int id, String name, String phone, String license, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.license = license;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getLicense() { return license; }
    public String getAddress() { return address; }
    
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setLicense(String license) { this.license = license; }
    public void setAddress(String address) { this.address = address; }
}