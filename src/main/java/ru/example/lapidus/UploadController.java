package ru.example.lapidus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.helpers.DefaultHandler;
import ru.example.lapidus.interfaces.MyNode;
import ru.example.lapidus.interfaces.MyXMLParser;
import ru.example.lapidus.model.CustomerList;
import ru.example.lapidus.service.MyXMLParserImpl1;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
    @ResponseBody
    public Map<String, String> handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("threshold") int threshold){
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
                    //return "You sick bastard uploaded not valid file!";
                    return null;
                }
                //we have to recreate stream as validation closes it
                xml = new FileInputStream(new File(name));
                //InputStream xsd = new FileInputStream(new File("scheme.xsd"));
                //this.getClass().getResourceAsStream("scheme.xds");
                //MyXMLParser parser = new MyXMLParserImpl1();

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                //MyXMLParserImpl1 mp = new MyXMLParserImpl1();
                saxParser.parse(xml, (DefaultHandler) parser);
                CustomerList top = (CustomerList) parser.getTop();
                //return "You successfully uploaded " + name + "!";
                Map<String, String> response = new HashMap<>();
                response.put("total", top.getTotal()+"");
                response.put("max_client", top.getMaxClient());
                response.put("max_order", top.getMaxOrder() + "");
                response.put("min_order", top.getMinOrder() + "");
                response.put("order_num", top.getOrderNum() + "");
                response.put("avg_cost", top.getAvgPrice() + "");
                //return new ModelAndView("response", response);
                return response;
            } catch (Exception e) {
                //return "You failed to upload " + name + " => " + e.getMessage();
                return null;
            }
        } else {
            //return "You failed to upload " + name + " because the file was empty.";
            return null;
        }
    }
}
