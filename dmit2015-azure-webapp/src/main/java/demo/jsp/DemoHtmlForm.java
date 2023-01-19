package demo.jsp;

import lombok.Data;

@Data
public class DemoHtmlForm {

    // Textfield form field
    private String textValue = "sample text";
    private int numberValue = 1;

    // Radio Button form field
    private String radioValue = "radio1";

    public String radioSelectionAttribute(String radioSelected) {
        if (radioValue != null) {
            return radioValue.equals(radioSelected) ? "checked=\"checked\"" : "";
        }
        return "";
    }

    // Text Area form field
    private String textareaValue = "no comments";

    // Select form field
    private String selectValue = "Select 1";

    public String selectSelectionAttribute(String selectedValue) {
        if (selectValue != null) {
            return selectValue.equals(selectedValue) ? "selected=\"selected\"" : "";
        }
        return "";
    }

    // Multiple Select
    private String[] multipleSelectValues;

    public String multipleSelectSelection(String selectedValue) {
        if (multipleSelectValues != null) {
            for (int index = 0; index < multipleSelectValues.length; ++index) {
                if (multipleSelectValues[index].equals(selectedValue)) {
                    return "selected=\"selected\"";
                }
            }
        }
        return "";
    }

    // Multiple Checkboxes form field
    private String[] multipleCheckboxValues;

    public String multipleCheckboxSelection(String checkbox) {
        if (multipleCheckboxValues != null) {
            for (int index = 0; index < multipleCheckboxValues.length; ++index) {
                if (multipleCheckboxValues[index].equals(checkbox)) {
                    return "checked=\"checked\"";
                }
            }
        }
        return "";
    }

    // Single Checkbox form field;
    private boolean checkbox;

    public String greetingMessage() {
        return "";
    }

}