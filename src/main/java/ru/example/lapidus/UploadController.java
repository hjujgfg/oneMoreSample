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
import ru.example.lapidus.interfaces.MyXMLParser;
import ru.example.lapidus.model.CustomerList;

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

    /*@RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "parseres";
    }*/

    @RequestMapping(value="/upload")
    //@ResponseBody
    public ModelAndView handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("threshold") int threshold, ModelAndView model){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("tmp/"+name)));
                stream.write(bytes);
                stream.close();
                InputStream xml = new FileInputStream(new File("tmp/"+name));
                Resource resXsd = resourceLoader.getResource("classpath:scheme.xsd");
                InputStream xsd = resXsd.getInputStream();
                if (!parser.isValid(xml, xsd)){
                    return null;
                }
                xml = new FileInputStream(new File("tmp/"+name));

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                saxParser.parse(xml, (DefaultHandler) parser);

                CustomerList top = (CustomerList) parser.getTop();
                Map<String, String> response = new HashMap<>();
                response.put("total", top.getTotal()+"");
                response.put("max_client", top.getMaxClient());
                response.put("max_order", top.getMaxOrder() + "");
                response.put("min_order", top.getMinOrder() + "");
                response.put("order_num", top.getOrderNum() + "");
                response.put("avg_cost", top.getAvgPrice() + "");
                //return new ModelAndView("response", response);
                //return response;
                //ModelAndView mav = new ModelAndView("jsp/upload");
                //mav.addAllObjects(response);
                //model.addAllAttributes(response);
                model.setViewName("parseres");
                model.addAllObjects(response);
                return model;
                //return "parseres";
                //return mav;
            } catch (Exception e) {
                e.printStackTrace();
                //return "You failed to upload " + name + " => " + e.getMessage();
                return null;
            }
        } else {
            //return "You failed to upload " + name + " because the file was empty.";
            return null;
        }
    }
}
