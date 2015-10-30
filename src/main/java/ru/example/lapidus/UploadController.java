package ru.example.lapidus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.DefaultHandler;
import ru.example.lapidus.interfaces.MyXMLParser;
import ru.example.lapidus.model.CustomerList;
import ru.example.lapidus.service.MyXMLParserImpl1;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

/**
 * Created by Егор on 20.10.2015.
 */
@Controller
public class UploadController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MyXMLParser parser;

    @RequestMapping(value = "/upload", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String upload() {
        return "shit";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                InputStream xml = new FileInputStream(new File(name));
                //InputStream xsd = UploadController.class.getResourceAsStream("scheme.xsd");
                Resource resXsd = resourceLoader.getResource("classpath:scheme.xsd");
                InputStream xsd = resXsd.getInputStream();
                if (!parser.isValid(xml, xsd)){
                    return "You sick bastard uploaded not valid file!";
                }
                //InputStream xsd = new FileInputStream(new File("scheme.xsd"));
                //this.getClass().getResourceAsStream("scheme.xds");
                //MyXMLParser parser = new MyXMLParserImpl1();

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                //MyXMLParserImpl1 mp = new MyXMLParserImpl1();
                saxParser.parse(xml, (DefaultHandler) parser);
                CustomerList top = (CustomerList) parser.getTop();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
