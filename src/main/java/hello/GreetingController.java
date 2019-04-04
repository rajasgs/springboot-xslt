package hello;

import java.io.File;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
     */
    @GetMapping("/users")
    public String getUsers(@RequestParam(name="city", required=false, defaultValue="Toronto") String name, Model model) throws TransformerException {
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
    
    public static String BASE_FOLDER = "/Users/str-kwml0011/jprojects/shaks200/";
    
    /**
     * 
     * https://stackoverflow.com/questions/13217657/how-to-convert-stream-results-to-string
     * @throws TransformerException 
     */
    public String tr() throws TransformerException {
    	
    	String xmlFile = BASE_FOLDER+"movie.xml";
    	String xslFile = BASE_FOLDER+"movie.xsl";
    	
    	//create a StringWriter for the output
    	StringWriter outWriter = new StringWriter();
    	StreamResult result = new StreamResult( outWriter );
    	
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(xslFile)));
	    //transformer.transform(new StreamSource(new File(xmlFile)), new StreamResult(System.out));
	    
	    StreamSource source = new StreamSource(new File(xmlFile));
    	
    	transformer.transform( source, result );  
    	StringBuffer sb = outWriter.getBuffer(); 
    	String finalstring = sb.toString();
    	
    	return finalstring;
    }
}
