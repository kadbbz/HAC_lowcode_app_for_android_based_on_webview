package com.huozige.lab.container.offlineform.model.formitem;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SelectFormItem extends BaseFormItem {
    @Getter
    @AllArgsConstructor
    public static class Option {
        private String value;
        private String displayText;
    }

    private List<Option> options = new ArrayList<>();
    private String selectedValue;
    private String selectedDisplayText;

    public SelectFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
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
