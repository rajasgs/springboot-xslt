package hello;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	/**
	 * 
	 * @param name
	 * @param model
	 * @return
	 * 
	 * possible urls:
	 * 		http://localhost:8080/greeting
	 * 		http://localhost:8080/greeting?name=Raja
	 */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    /**
     * 
     * @param name
     * @param model
     * @return
     * 
     * possible urls:
	 * 		http://localhost:8080/users
     * @throws TransformerException 
     * @throws IOException 
     */
    @GetMapping("/users")
    public String getUsers(@RequestParam(name="city", required=false, defaultValue="Toronto") String name, Model model) throws TransformerException, IOException {
        model.addAttribute("city", name);
        
        List<String> userList = new LinkedList<String>();
        userList.add("Raja");
        userList.add("Che");
        userList.add("Somya");   
        
        model.addAttribute("users", userList);
        
        String html_content = "<p style=\"color:red\">Some html content</p>";
        model.addAttribute("html_content", html_content);
        
        String movie = tr();
        model.addAttribute("movie", movie);
        
        return "users";
    }
    
    public static String BASE_FOLDER = "";
    
    /**
     * 
     * https://stackoverflow.com/questions/13217657/how-to-convert-stream-results-to-string
     * @throws TransformerException 
     * @throws IOException 
     */
    public String tr() throws TransformerException, IOException {
    	
    	String xmlFile = BASE_FOLDER+"movie.xml";
    	String xslFile = BASE_FOLDER+"movie.xsl";
    	
    	Resource resource = new ClassPathResource(xmlFile);
    	File xmlFilePath = resource.getFile();

    	Resource resource1 = new ClassPathResource(xslFile);
    	File xslFilePath = resource1.getFile();
    	
    	//create a StringWriter for the output
    	StringWriter outWriter = new StringWriter();
    	StreamResult result = new StreamResult( outWriter );
    	
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFilePath));
	    //transformer.transform(new StreamSource(new File(xmlFile)), new StreamResult(System.out));
	    
	    StreamSource source = new StreamSource(xmlFilePath);
    	
    	transformer.transform( source, result );  
    	StringBuffer sb = outWriter.getBuffer(); 
    	String finalstring = sb.toString();
    	
    	return finalstring;
    }
}
