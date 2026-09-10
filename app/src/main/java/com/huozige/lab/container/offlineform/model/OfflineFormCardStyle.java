package com.huozige.lab.container.offlineform.model;

import lombok.Data;

@Data
public class OfflineFormCardStyle {
    public String type = "text";
    public String content = "";
    public String property = "";
    public String fontSize = "medium";
    public boolean bold;
}
