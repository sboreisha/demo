
package com.exadel.automation.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Components implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("componentSelector")
    @Expose
    private ComponentSelector componentSelector;
    @SerializedName("element")
    @Expose
    private List<Elements> element = new ArrayList<Elements>();
    private final static long serialVersionUID = 1953258167824939486L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentSelector getComponentSelector() {
        return componentSelector;
    }

    public void setComponentSelector(ComponentSelector componentSelector) {
        this.componentSelector = componentSelector;
    }

    public List<Elements> getElements() {
        return element;
    }

    public void setElement(List<Elements> element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("componentSelector", componentSelector).append("element", element).toString();
    }

}
