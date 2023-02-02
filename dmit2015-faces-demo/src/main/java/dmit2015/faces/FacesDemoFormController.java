package dmit2015.faces;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@Named("currentFacesDemoFormController")
@RequestScoped
public class FacesDemoFormController {

	// Textfield form field
	@Getter @Setter
	private String textValue = "sample text";
	@Getter @Setter
	private int numberValue = 1;
	// Select form field
	@Getter @Setter
	private String selectValue = "selectedValue1";


	// Multiple Select
	@Getter
	private String[] multipleSelectOptions = {"Multiple Select 1","Multiple Select 2","Multiple Select 3","Multiple Select 4","Multiple Select 5"};
	@Getter @Setter
	private String[] multipleSelectedValues;

	// Multiple Checkboxes form field
	@Getter
	private String[] multipleCheckboxOptions = {"Multiple Check 1","Multiple Check 2","Multiple Check 3","Multiple Check 4","Multiple Check 5"};
	@Getter @Setter
	private String[] multipleCheckboxValues;

	// Radio Button form field
	@Getter
	private String[] radioOptions = {"Radio 1","Radio 2","Radio 3"};
	@Getter @Setter
	private String radioValue = "Radio 1";

	// Text Area form field
	@Getter @Setter
	private String textareaValue = "no comments";

	// Single Checkbox form field;
	@Getter @Setter
	private boolean checkbox;

    // Date form field
	@Getter @Setter
	private LocalDate dateValue = LocalDate.now();


	public String submit() {
		Messages.addGlobalInfo("Text value is {0}", textValue);
		Messages.addGlobalInfo("Number value is {0}", numberValue);
		Messages.addGlobalInfo("Select One value is {0}", selectValue);
		Messages.addGlobalInfo("Multiple Selected values are {0}", Arrays.stream(multipleSelectedValues).collect(Collectors.joining(",")));
		Messages.addGlobalInfo("Multiple Checkbox values are {0}", Arrays.stream(multipleCheckboxValues).collect(Collectors.joining(",")));
		Messages.addGlobalInfo("Radio value is {0}", radioValue);
		Messages.addGlobalInfo("Textarea value is {0}", textareaValue);
		Messages.addGlobalInfo("Checked Boolean is {0}", checkbox);
        Messages.addGlobalInfo("Date value is {0}", dateValue.toString());

		return "";
	}

}