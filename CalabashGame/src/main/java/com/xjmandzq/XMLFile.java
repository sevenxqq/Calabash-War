
/*
文件类：保存进度和读取进度
    属性：
    构造器：
    方法：

*/



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
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

class XMLFile {
    Document dcmt;
    Element fileroot;
    boolean newround = true; //如果后续有加入再来一局的功能
    Element battle;
    public XMLFile() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            dcmt = db.newDocument();
            fileroot = dcmt.createElement("Calabash-VS-Monster");
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            Element createtime = dcmt.createElement("createtime");
            Text m = dcmt.createTextNode(time);
            createtime.appendChild(m);
            fileroot.appendChild(createtime);
            dcmt.appendChild(fileroot);
            battle = dcmt.createElement("battle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFile() {

    }

    public void saveFile() throws TransformerException {
        fileroot.appendChild(battle);
        TransformerFactory tff=TransformerFactory.newInstance();
        Transformer tf=tff.newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        String time = new SimpleDateFormat("yyyy:MM:dd-hh:mm:ss").format(new Date());
        tf.transform(new DOMSource(dcmt), new StreamResult(new File(time+".xml")));
        
    }
    public void writeIn(ActionType atype,String str){
        if(newround == true){
            newround = false;
            NodeList nodelist = battle.getChildNodes();
            if (nodelist.getLength()!=0)
                fileroot.appendChild(battle);
            battle = dcmt.createElement("battle");
        }
        switch(atype){
            case MOVE: writeInMove(str); break;
            case GNRATK: writeInGnrAtk(str); break;
            case HEEL: writeInHeel(str); break;
            default:break;
        }
    }
    public void writeInMove(String str){
        Element move=dcmt.createElement("move");
        String [] arr = str.split("\\s+"); 
        move.setAttribute("ChatId",arr[0]);
        move.setAttribute("dir",arr[1]);
        Text m=dcmt.createTextNode("" + arr[0] +" move " + arr[1]);
        move.appendChild(m);
        battle.appendChild(move);
    }

    public void writeInGnrAtk(String str){
        Element gnrAtk=dcmt.createElement("gnrAtk");
        String [] arr = str.split("\\s+"); 
        gnrAtk.setAttribute("ChatId",arr[0]);
        gnrAtk.setAttribute("dir",arr[1]);
        Text m=dcmt.createTextNode("" + arr[0] +" use genAtk and the dir is " + arr[1]);
        gnrAtk.appendChild(m);
        battle.appendChild(gnrAtk);
    }

    public void writeInHeel(String str){
        Element heel=dcmt.createElement("heel");
        String [] arr = str.split("\\s+"); 
        heel.setAttribute("ChatId",arr[0]);
        heel.setAttribute("dir",arr[1]);
        Text m=dcmt.createTextNode("" + arr[0] +" use heeling and the dir is " + arr[1]);
        heel.appendChild(m);
        battle.appendChild(heel);
    }
    public void parseIn(){

    }
    public static void main(String []args) throws Exception{
        XMLFile test=new XMLFile();
        test.writeIn(ActionType.MOVE, "3 UP");
        test.writeIn(ActionType.GNRATK, "4 DOWN");
        test.writeIn(ActionType.HEEL, "5 RIGHT");
        test.saveFile();
    }

}