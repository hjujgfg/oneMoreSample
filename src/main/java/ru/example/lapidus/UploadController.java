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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.xml.sax.helpers.DefaultHandler;
import ru.example.lapidus.interfaces.MyXMLParser;
import ru.example.lapidus.model.CustomerList;

import javax.management.modelmbean.ModelMBean;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    @RequestMapping(value="/upload", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam(value = "threshold", defaultValue = "0") int threshold, ModelAndView model){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("tmp/"+name)));
                stream.write(bytes);
                stream.close();
                File tmp = new File("tmp");
                tmp.mkdir();
                InputStream xml = new FileInputStream(new File("tmp/"+name));
                Resource resXsd = resourceLoader.getResource("classpath:scheme.xsd");
                InputStream xsd = resXsd.getInputStream();
                if (!parser.isValid(xml, xsd)){
                    return "Файл не прошел валидацию";
                }
                xml = new FileInputStream(new File("tmp/"+name));

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                saxParser.parse(xml, (DefaultHandler) parser);

                CustomerList top = (CustomerList) parser.getTop();
                Map<String, String> response = new LinkedHashMap<>();
                response.put("total", top.getTotal() + "");
                response.put("max_client", top.getMaxClient());
                response.put("max_order", top.getMaxOrder() + "");
                response.put("min_order", top.getMinOrder() + "");
                response.put("order_num", top.getOrderNum() + "");
                response.put("avg_cost", top.getAvgPrice() + "");
                if (threshold != 0) {
                    response.put("filtered", top.getFilteredCustomers((double) threshold));
                }
                //this is mock for implementing actual view
                model.addAllObjects(response);
                model.setViewName("result");
                return buildResult(response);
            } catch (Exception e) {
                e.printStackTrace();
                return "Произошла ошибочка";
            }
        } else {
            return "Файл пустой!";
        }
    }

    //did not have time to implement normal view, so we will generate some kind of HTML markup
    public String buildResult(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String str : map.keySet()) {
            switch (str) {
                case "total":
                    sb.append("Общая сумма: " + map.get(str) + "<br>");
                    break;
                case "max_client":
                    sb.append("Клиент с максимальным заказом: " + map.get(str) + "<br>");
                    break;
                case "max_order":
                    sb.append("Сумма максимального заказа: " + map.get(str) + "<br>");
                    break;
                case "min_order":
                    sb.append("Сумма минимального заказа: " + map.get(str) + "<br>");
                    break;
                case "order_num":
                    sb.append("Количество заказов: " + map.get(str) + "<br>");
                    break;
                case "avg_cost":
                    sb.append("Средняя сумма заказа: " + map.get(str) + "<br>");
                    break;
                case "filtered":
                    sb.append("Клиенты с большими заказами: " + map.get(str) + "<br>");
                    break;
            }
        }
        return sb.toString();
    }
}
