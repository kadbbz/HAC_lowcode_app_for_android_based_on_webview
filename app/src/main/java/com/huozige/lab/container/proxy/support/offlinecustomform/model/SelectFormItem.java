package com.huozige.lab.container.proxy.support.offlinecustomform.model;

import java.util.ArrayList;
import java.util.List;

public class SelectFormItem extends BaseFormItem {
    public static class Option {
        private String value;
        private String displayText;

        public Option(String value, String displayText) {
            this.value = value;
            this.displayText = displayText;
        }

        public String getValue() { return value; }
        public String getDisplayText() { return displayText; }
    }

    private List<Option> options = new ArrayList<>();
    private String selectedValue;
    private String selectedDisplayText;

    public SelectFormItem(String id, String title, String hint, boolean required) {
        super(id, title, hint, required);
    }

    @Override
    public int getItemType() {
        return TYPE_SELECT;
    }

    @Override
    public String getValue() {
        return selectedValue;
    }

    public void setSelectedValue(String value) {
        this.selectedValue = value;
        for (Option option : options) {
            if (option.getValue().equals(value)) {
                this.selectedDisplayText = option.getDisplayText();
                break;
            }
        }
    }

    public String getSelectedDisplayText() {
        return selectedDisplayText;
    }

    public void addOption(String value, String displayText) {
        options.add(new Option(value, displayText));
    }

    public List<Option> getOptions() {
        return new ArrayList<>(options);
    }

    @Override
    public boolean validate() {
        clearError();

        if (isRequired() && (selectedValue == null || selectedValue.isEmpty())) {
            setErrorMessage("请选择" + getTitle());
            return false;
        }

        return true;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
    }
}
