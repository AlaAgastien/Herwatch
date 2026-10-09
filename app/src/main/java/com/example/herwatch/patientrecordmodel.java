package com.example.herwatch;

public class patientrecordmodel {
    String id,docname,doc_contact,patientname,patientnum,patientage,dateselect,timeselect,status;

    public patientrecordmodel(String id, String docname, String doc_contact, String patientname, String patientnum, String patientage, String dateselect, String timeselect, String status) {
        this.id = id;
        this.docname = docname;
        this.doc_contact = doc_contact;
        this.patientname = patientname;
        this.patientnum = patientnum;
        this.patientage = patientage;
        this.dateselect = dateselect;
        this.timeselect = timeselect;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDocname() {
        return docname;
    }

    public String getDoc_contact() {
        return doc_contact;
    }

    public String getPatientname() {
        return patientname;
    }

    public String getPatientnum() {
        return patientnum;
    }

    public String getPatientage() {
        return patientage;
    }

    public String getDateselect() {
        return dateselect;
    }

    public String getTimeselect() {
        return timeselect;
    }

    public String getStatus() {
        return status;
    }
}
