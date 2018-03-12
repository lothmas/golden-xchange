//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.utilities;

import org.springframework.beans.factory.annotation.Value;

public class GlobalSettings {
    @Value("${WEBSERVICE.URL}")
    private String webservice_URL;
    @Value("${WEBSERVICE.PORT}")
    private String webservice_PORT;
    @Value("${WEBSERVICE.SERVICE}")
    private String webservice_SERVICE;

    public GlobalSettings() {
    }

    public String getWebservice_URL() {
        return this.webservice_URL;
    }

    public void setWebservice_URL(String webservice_URL) {
        this.webservice_URL = webservice_URL;
    }

    public String getWebservice_PORT() {
        return this.webservice_PORT;
    }

    public void setWebservice_PORT(String webservice_PORT) {
        this.webservice_PORT = webservice_PORT;
    }

    public String getWebservice_SERVICE() {
        return this.webservice_SERVICE;
    }

    public void setWebservice_SERVICE(String webservice_SERVICE) {
        this.webservice_SERVICE = webservice_SERVICE;
    }
}

