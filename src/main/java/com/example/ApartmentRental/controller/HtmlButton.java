package com.example.ApartmentRental.controller;

public class HtmlButton {
    private String buttonLabel;
    private String href;

    public String getLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public HtmlButton(String buttonLabel, String href) {
        this.buttonLabel = buttonLabel;
        this.href = href;
    }
}
