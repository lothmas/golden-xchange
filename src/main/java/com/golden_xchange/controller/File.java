
package com.golden_xchange.controller;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "url",
    "thumbnailUrl",
    "name",
    "type",
    "size",
    "deleteUrl",
    "deleteType",
    "error"
})
public class File {




    @JsonProperty("url")
    private String url;
    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("deleteUrl")
    private String deleteUrl;
    @JsonProperty("deleteType")
    private String deleteType;
    @JsonProperty("error")
    private String error;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("thumbnailUrl")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @JsonProperty("thumbnailUrl")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("deleteUrl")
    public String getDeleteUrl() {
        return deleteUrl;
    }

    @JsonProperty("deleteUrl")
    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    @JsonProperty("deleteType")
    public String getDeleteType() {
        return deleteType;
    }

    @JsonProperty("deleteType")
    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(String error) {
        this.error = error;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
