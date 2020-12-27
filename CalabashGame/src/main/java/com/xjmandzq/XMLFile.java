
/*
文件类：保存进度和读取进度
    属性：
    构造器：
    方法：

*/

// TODO：约定xml文件格式 <cmd> seqnu id + parm </cmd>

package com.xjmandzq;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

class XMLFile {
    Document dcmt;
    Element battleroot;

    public XMLFile() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            dcmt = db.newDocument();
            battleroot = dcmt.createElement("Calabash-VS-Monster");
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            Element createtime = dcmt.createElement("createtime");
            Text m = dcmt.createTextNode(time);
            createtime.appendChild(m);
            battleroot.appendChild(createtime);
            dcmt.appendChild(battleroot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFile() {

    }

    public void saveFile() throws TransformerException {
        TransformerFactory tff=TransformerFactory.newInstance();
        Transformer tf=tff.newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        String time = new SimpleDateFormat("yyyyMMdd-hhmm:ss").format(new Date());
        tf.transform(new DOMSource(dcmt), new StreamResult(new File(time+".xml")));
        
    }
    public void writeIn(){

    }
    public void parseIn(){

    }
    public static void main(String []args) throws Exception{
        XMLFile test=new XMLFile();
        test.saveFile();
    }

}