package com.example.herwatch;

public class frdsmodel {
    String id,name,phonenumber,fnmae,category,fphonenumber;

    public frdsmodel(String id, String name, String phonenumber, String fnmae, String category, String fphonenumber) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.fnmae = fnmae;
        this.category = category;
        this.fphonenumber = fphonenumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getFnmae() {
        return fnmae;
    }

    public String getCategory() {
        return category;
    }

    public String getFphonenumber() {
        return fphonenumber;
    }
}
