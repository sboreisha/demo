
package com.hpe.automation.pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ElementSelector implements Serializable
{

    @SerializedName("how")
    @Expose
    private String how;
    @SerializedName("selector")
    @Expose
    private String selector;
    private final static long serialVersionUID = -7799549173885669488L;

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("how", how).append("selector", selector).toString();
    }

}
