
package com.exadel.automation.pojo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ElementCheckings implements Serializable {

    @SerializedName("fontSize")
    @Expose
    private String fontSize;
    @SerializedName("padding")
    @Expose
    private String padding;
    @SerializedName("anything")
    @Expose
    private String anything;
    @SerializedName("rendition")
    @Expose
    private String rendition;

    public String getRendition() {
        return rendition;
    }

    public void setRendition(String rendition) {
        this.rendition = rendition;
    }

    private final static long serialVersionUID = 56840738426372587L;

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getAnything() {
        return anything;
    }

    public void setAnything(String anything) {
        this.anything = anything;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("fontSize", fontSize).append("padding", padding).append("anything", anything).toString();
    }

}
