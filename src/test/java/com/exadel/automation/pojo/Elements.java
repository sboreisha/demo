
package com.exadel.automation.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Elements implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("elementSelector")
    @Expose
    private ElementSelector elementSelector;
    @SerializedName("elementCheckings")
    @Expose
    private List<ElementChecking> elementCheckings = new ArrayList<ElementChecking>();
    @SerializedName("relativeLocation")
    @Expose
    private RelativeLocation relativeLocation;
    private final static long serialVersionUID = 8845944588667244941L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementSelector getElementSelector() {
        return elementSelector;
    }

    public void setElementSelector(ElementSelector elementSelector) {
        this.elementSelector = elementSelector;
    }

    public List<ElementChecking> getElementCheckings() {
        return elementCheckings;
    }

    public void setElementCheckings(List<ElementChecking> elementCheckings) {
        this.elementCheckings = elementCheckings;
    }

    public RelativeLocation getRelativeLocation() {
        return relativeLocation;
    }

    public void setRelativeLocation(RelativeLocation relativeLocation) {
        this.relativeLocation = relativeLocation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("elementSelector", elementSelector).append("elementCheckings", elementCheckings).append("relativeLocation", relativeLocation).toString();
    }

}
