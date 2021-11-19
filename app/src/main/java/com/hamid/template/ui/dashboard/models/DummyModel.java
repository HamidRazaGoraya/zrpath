package com.hamid.template.ui.dashboard.models;

public class DummyModel {
    private String InstitutionName="";
    private String DegreeName="";
    private String Duration="";

    public DummyModel() {
        InstitutionName = "";
        DegreeName = "";
        Duration = "";
    }

    public String getInstitutionName() {
        return InstitutionName;
    }

    public void setInstitutionName(String institutionName) {
        InstitutionName = institutionName;
    }

    public String getDegreeName() {
        return DegreeName;
    }

    public void setDegreeName(String degreeName) {
        DegreeName = degreeName;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
