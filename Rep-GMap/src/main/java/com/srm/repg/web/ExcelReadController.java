package com.srm.repg.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.srm.repg.form.FileParams;
import com.srm.repg.service.ExcelReadDataService;

@Controller
public class ExcelReadController {
	
	@Value("${uploadfilePath}")
	private String uploadPath;

	@RequestMapping(value = "/readAddress", method = RequestMethod.GET)
	public ModelAndView listAddess() {
		return new ModelAndView("readAddress", "command", new FileParams());
	}

	@Autowired
	ExcelReadDataService excelReadDataService;

	@RequestMapping(value = "/address/list/{id}", method = RequestMethod.GET)
	public String listemployee(Model model, @PathVariable(name = "id") String id) {
		System.out.println("Date:" + id);
		model.addAttribute("fileParams", excelReadDataService.getListOfData(id));
		//return "listAddress";
		return "readAddress";
	}

	@RequestMapping(value = "/address/readAddress", method = RequestMethod.POST)
	public String readAddress(@ModelAttribute("fileParams") FileParams param, ModelMap model,BindingResult result) {
		// Print input date
	    boolean error = false;
	    if(param.getDateVal().isEmpty()){
	    	result.rejectValue("dateVal", "error.firstName");
	        error = true;
	    }
		return "redirect:/address/list/" + param.getDateVal() + "";
	}

	
	@PostMapping("/upload") // //new annotation since 4.3
	public String fileUpload(@RequestParam("file") MultipartFile file,@ModelAttribute("fileParams") FileParams param, RedirectAttributes redirectAttributes,BindingResult validationResult) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			/*ImageIcon icon = new ImageIcon("src/main/webapp/resources/assets/images/logo.png"); //object for displaying a custom icon
		    final JPanel panel = new JPanel(); // the object for the pop-up
		    int test_for_commit_to_challenge = JOptionPane.showConfirmDialog(panel, "Do you want to proceed with the challenge?", "WARNING!",
		    		JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, icon); //this displays the pop-up and returns an integer depending on what the user clicks
		    System.out.println("test int is " + test_for_commit_to_challenge);
		    if (test_for_commit_to_challenge == 2) {
		    		//System.out.println("cancelled");
		    	return "redirect:uploadAdd";
		    }
			*/
		}
		try {
			String outFilePath = uploadPath+param.getDateVal()+"/";
			File directory = new File(outFilePath);
			if (!directory.exists()) {
				directory.mkdirs();
			} else {
			}
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(outFilePath + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadstatus";
	}
	
	 @GetMapping("/uploadstatus")
	    public String uploadStatus() {
	        return "upload";
	    }

	 @GetMapping("/uploadAdd")
	    public String uploadAdd() {
	        return "upload";
	    }
}
