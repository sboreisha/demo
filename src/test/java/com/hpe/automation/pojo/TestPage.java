
package com.hpe.automation.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TestPage implements Serializable
{

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("windowSize")
    @Expose
    private WindowSize windowSize;
    @SerializedName("component")
    @Expose
    private List<Components> component = new ArrayList<Components>();
    private final static long serialVersionUID = 6536125426923012145L;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WindowSize getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(WindowSize windowSize) {
        this.windowSize = windowSize;
    }

    public List<Components> getComponents() {
        return component;
    }

    public void setComponent(List<Components> component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("url", url).append("windowSize", windowSize).append("component", component).toString();
    }

}
