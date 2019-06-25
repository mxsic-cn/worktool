package cn.siqishangshu.pingan.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class BankInterface {

    /*生成请求银行的完整报文*/
    public String getTranMessage(HashMap parmaKeyDict)
    {

        byte[] byteMessageBody;
        String tranMessage="";
        String tranMessageBody="";

        /*组业务报文体*/
        tranMessageBody=getTranMessageBody(parmaKeyDict);

        try{
            byteMessageBody = tranMessageBody.getBytes("gbk");//编码
        }catch(UnsupportedEncodingException ex)
        {
            return ex.toString();
        }

        /*组公网业务报文头*/
        int iLength=byteMessageBody.length;
        String hLength=String.format("%08d", iLength);
        String tranMessageHead=getTranMessageHead(hLength,parmaKeyDict);

        /*组公网通讯报文头*/
        int iNetLength=iLength+122;
        String hNetLength=String.format("%010d", iNetLength);
        String tranMessageNetHead=getTranMessageNetHead(hNetLength,parmaKeyDict);

        /*组完整请求报文*/
        tranMessage=tranMessageNetHead+tranMessageHead+tranMessageBody;

        return tranMessage;
    }
    /*生成请求银行报文的报文头*/
    private String getTranMessageNetHead(String hLength,HashMap parmaKeyDict)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

        String netHeadPart1="A001030101";
        String netHeadPart2="                ";//16个空格
        String hTradeCode="000000";
        String hServType="01";
        //String hMacCode="                ";
        String hTrandateTime=df.format(new Date());
        String hRspCode="999999";
        String hRspMsg="                                                                                                    ";//100个空格
        String hConFlag="0";
        String hCounterId="PA001";
        String hTimes="000";
        String hSignFlag="0";
        String hSignPacketType="0";
        String netHeadPart3="            ";//12个空格
        String netHeadPart4="00000000000";


        String hThirdLogNo=(String)parmaKeyDict.get("ThirdLogNo");
        String hQydm=(String)parmaKeyDict.get("Qydm");

        String tranMessageNetHead=netHeadPart1+hQydm+netHeadPart2+hLength+hTradeCode+hCounterId+hServType+hTrandateTime+hThirdLogNo+
                hRspCode+hRspMsg+hConFlag+hTimes+hSignFlag+hSignPacketType+netHeadPart3+netHeadPart4;

        return tranMessageNetHead;
    }

    /*生成请求银行报文的报文头*/
    private String getTranMessageHead(String hLength,HashMap parmaKeyDict)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

        String hServType="01";
        String hMacCode="                ";
        String hTrandateTime=df.format(new Date());
        String hRspCode="999999";
        String hRspMsg="                                          ";
        String hConFlag="0";
        String hCounterId="PA001";

        String hTranFunc=(String)parmaKeyDict.get("TranFunc");
        String hThirdLogNo=(String)parmaKeyDict.get("ThirdLogNo");
        String hQydm=(String)parmaKeyDict.get("Qydm");

        String tranMessageHead=hTranFunc+hServType+hMacCode+hTrandateTime+hRspCode+hRspMsg+
                hConFlag+hLength+hCounterId+hThirdLogNo+hQydm;

        return tranMessageHead;
    }


    /*根据交易码调用不同的交易报文生成方法*/
    private String getTranMessageBody(HashMap parmaKeyDict)
    {
        String tranMessageBody="";
        int hTranFunc=Integer.parseInt((String)parmaKeyDict.get("TranFunc"));
        switch(hTranFunc)
        {
            case 1330:tranMessageBody=getTranMessageBody_1330(parmaKeyDict);break;
        }

        return tranMessageBody;
    }

    /*生成1330交易的报文体*/
    private String getTranMessageBody_1330(HashMap parmaKeyDict)
    {
        String FuncFlag="";
        String TxDate="";
        String Reserve="";


        if(parmaKeyDict.containsKey("FuncFlag"))
        {
            FuncFlag=(String)parmaKeyDict .get("FuncFlag");
        }

        if(parmaKeyDict.containsKey("TxDate"))
        {
            TxDate=(String)parmaKeyDict .get("TxDate");
        }

        if(parmaKeyDict.containsKey("Reserve"))
        {
            Reserve=(String)parmaKeyDict .get("Reserve");
        }

        String tranMessageBody=FuncFlag+"&"+TxDate+"&"+Reserve+"&";

        return tranMessageBody;

    }

    /*解析接收银行的报文，入参String类型*/
    public HashMap parsingTranMessageString(String TranMessage)
    {
        HashMap retKeyDict = new HashMap();
        int i;
        byte[] bNetHead=new byte[222];
        byte[] bTranFunc=new byte[226];
        byte[] bRspCode=new byte[93];
        byte[] bRspMsg=new byte[193];
        byte[] bHeadMsg=new byte[344];

        /*获取返回码*/
        try{
            byte[]   byteRetMessage = TranMessage.getBytes("gbk");//编码
            for(i=0;i<93;i++)
            {
                bRspCode[i]=byteRetMessage[i];
            }
            String sRspCode= new String(bRspCode,"gbk");
            sRspCode=sRspCode.substring(87);
            retKeyDict.put("RspCode",sRspCode);
        }catch(UnsupportedEncodingException ex)
        {
            System.out.println(ex.toString());
        }

        /*获取返回信息*/
        try{
            byte[]   byteRetMessage = TranMessage.getBytes("gbk");//编码
            for(i=0;i<193;i++)
            {
                bRspMsg[i]=byteRetMessage[i];
            }
            String sRspMsg= new String(bRspMsg,"gbk");
            sRspMsg=sRspMsg.substring(93);
            retKeyDict.put("RspMsgBak",sRspMsg);
            sRspMsg=sRspMsg.trim();
            retKeyDict.put("RspMsg",sRspMsg);
        }catch(UnsupportedEncodingException ex)
        {
            System.out.println(ex.toString());
        }

        String strCode=(String)retKeyDict.get("RspCode");


          /*获取交易码 */
        try{
            byte[]   byteRetMessage = TranMessage.getBytes("gbk");//编码
            for(i=0;i<226;i++)
            {
                bTranFunc[i]=byteRetMessage[i];
            }
            String sTranFunc= new String(bTranFunc,"gbk");

            for(i=0;i<222;i++)
            {
                bNetHead[i]=byteRetMessage[i];
            }
            String sNetHead= new String(bNetHead,"gbk");

            int iTranLength=sNetHead.length();
            sTranFunc=sTranFunc.substring(iTranLength);
            retKeyDict.put("TranFunc",sTranFunc);
        }catch(UnsupportedEncodingException ex)
        {
            System.out.println(ex.toString());
        }

        /*获取返回报文体*/
        try{
            byte[]   byteRetMessage = TranMessage.getBytes("gbk");//编码
            String sBodyMsg= new String(byteRetMessage,"gbk");//解码
            for(i=0;i<344;i++)
            {
                bHeadMsg[i]=byteRetMessage[i];
            }
            String sHeadMsg= new String(bHeadMsg,"gbk");
            //String strRspMsg=(String)retKeyDict.get("RspMsg");
            int iLength=sHeadMsg.length();
            sBodyMsg=sBodyMsg.substring(iLength);
            retKeyDict.put("BodyMsg",sBodyMsg);
        }catch(UnsupportedEncodingException ex)
        {
            System.out.println(ex.toString());
        }

        /*解析报文体*/
        spiltMessage(retKeyDict);




        return retKeyDict;


    }

    /*发送报文，并接收银行返回*/
    public void SendTranMessage(String tranMessage,String ServerIPAddress, int ServerPort,HashMap retKeyDict)
    {
        try {
            Socket s = new Socket(ServerIPAddress,ServerPort) ;
            s.setSendBufferSize(4096);
            s.setTcpNoDelay(true);
            s.setSoTimeout(60000);
            s.setKeepAlive(true);
            OutputStream os=s.getOutputStream();
            InputStream is=s.getInputStream();

            os.write(tranMessage.getBytes("gbk"));
            os.flush();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte []buf=new byte[4096];
            int len = -1;
            while ((len = is.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            byte[] byteContent = bos.toByteArray();
            String recvMessage = new String(byteContent, "gbk");
            recvMessage = recvMessage + '\0';//修复使用spilt方法时的数组越界错误
            retKeyDict.put("RecvMessage", recvMessage);

            os.close();
            is.close();


        } catch (IOException ex) {
            System.out.println(ex.toString());
        }


    }

    private void spiltMessage(HashMap retKeyDict)
    {

        int tranFunc=Integer.parseInt((String)retKeyDict.get("TranFunc"));
        switch(tranFunc)
        {
            case 1331:spiltMessage_1301(retKeyDict);break;
        }
    }

    private void spiltMessage_1301(HashMap retKeyDict)
    {
        if(retKeyDict.containsKey("BodyMsg"))
        {
            String bodyMessage=(String)retKeyDict.get("BodyMsg");
            String[] arryMessage=bodyMessage.split("&");
            String FuncFlag=arryMessage[0];
            String SupAcctId=arryMessage[1];
            String CustAcctId=arryMessage[2];
            String CustName=arryMessage[3];
            String ThirdCustId=arryMessage[4];
            String IdType=arryMessage[5];
            String IdCode=arryMessage[6];
            String CustFlag=arryMessage[7];
            String TotalAmount=arryMessage[8];
            String TotalBalance=arryMessage[9];
            String TotalFreezeAmount=arryMessage[10];
            String reserve=arryMessage[11];

            retKeyDict.put("FuncFlag", FuncFlag);
            retKeyDict.put("SupAcctId", SupAcctId);
            retKeyDict.put("CustAcctId", CustAcctId);
            retKeyDict.put("CustName", CustName);
            retKeyDict.put("ThirdCustId", ThirdCustId);
            retKeyDict.put("IdType", IdType);
            retKeyDict.put("IdCode", IdCode);
            retKeyDict.put("CustFlag", CustFlag);
            retKeyDict.put("TotalAmount", TotalAmount);
            retKeyDict.put("TotalBalance", TotalBalance);
            retKeyDict.put("TotalFreezeAmount", TotalFreezeAmount);
            retKeyDict.put("Reserve", reserve);
        }
    }

    /*生成返回银行的完整报文*/
    public String getReturnTranMessage(HashMap parmaKeyDict) throws UnsupportedEncodingException
    {

        byte[] byteMessageBody;
        String tranMessage="";
        String tranMessageBody="";
        String thirdLogNo=(String)parmaKeyDict.get("ThirdLogNo");
        String tranFunc=(String)parmaKeyDict.get("TranFunc");

            /*组业务报文体*/
        if(tranFunc.equals("1019"))
        {
            tranMessageBody=getReturnTranMessage_1019(parmaKeyDict);
        }else
        {
            tranMessageBody=thirdLogNo+"&";
        }

        try{
            byteMessageBody = tranMessageBody.getBytes("gbk");//编码
        }catch(UnsupportedEncodingException ex)
        {
            return ex.toString();
        }

            /*组公网业务报文头*/
        int iLength=byteMessageBody.length;
        String hLength=String.format("%08d", iLength);
        String tranMessageHead=getReturnTranMessageHead(hLength,parmaKeyDict);

            /*组公网通讯报文头*/
        int iNetLength=iLength+122;
        String hNetLength=String.format("%010d", iNetLength);
        String tranMessageNetHead=getReturnTranMessageNetHead(hNetLength,parmaKeyDict);

            /*组完整请求报文*/
        tranMessage=tranMessageNetHead+tranMessageHead+tranMessageBody;

        return tranMessage;
    }

    /*生成返回银行报文的报文头*/
    private String getReturnTranMessageNetHead(String hLength,HashMap parmaKeyDict) throws UnsupportedEncodingException
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

        String netHeadPart1="A001030101";
        String netHeadPart2="                ";//16个空格
        String hTradeCode="000000";
        String hServType="02";
        //String hMacCode="                ";
        String hTrandateTime=df.format(new Date());
        String hRspCode="999999";
        String hRspMsg="                                                                                                    ";//100个空格
        String hConFlag="0";
        String hCounterId="PA001";
        String hTimes="000";
        String hSignFlag="0";
        String hSignPacketType="0";
        String netHeadPart3="            ";//12个空格
        String netHeadPart4="00000000000";

        if(parmaKeyDict.containsKey("RspCode"))
        {
            hRspCode=(String)parmaKeyDict.get("RspCode");
        }

        if(parmaKeyDict.containsKey("RspMsg"))
        {
            hRspMsg=(String)parmaKeyDict.get("RspMsg");
        }

        int hLen=hRspMsg.getBytes("GBK").length;
        int iSpace=120-hLen;
        for(int i=0;i<iSpace;i++)
        {
            hRspMsg=hRspMsg+" ";
        }


        String hThirdLogNo=(String)parmaKeyDict.get("ThirdLogNo");
        String hQydm=(String)parmaKeyDict.get("Qydm");

        String tranMessageNetHead=netHeadPart1+hQydm+netHeadPart2+hLength+hTradeCode+hCounterId+hServType+hTrandateTime+hThirdLogNo+
                hRspCode+hRspMsg+hConFlag+hTimes+hSignFlag+hSignPacketType+netHeadPart3+netHeadPart4;

        return tranMessageNetHead;
    }

    /*生成返回银行报文的报文头*/
    private String getReturnTranMessageHead(String hLength,HashMap parmaKeyDict) throws UnsupportedEncodingException
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

        String hServType="02";
        String hMacCode="                ";
        String hTrandateTime=df.format(new Date());
        String hRspCode="999999";
        String hRspMsg="                                          ";
        String hConFlag="0";
        String hCounterId="PA001";

        String hTranFunc=(String)parmaKeyDict.get("TranFunc");
        String hThirdLogNo=(String)parmaKeyDict.get("ThirdLogNo");
        String hQydm=(String)parmaKeyDict.get("Qydm");

        if(parmaKeyDict.containsKey("RspCode"))
        {
            hRspCode=(String)parmaKeyDict.get("RspCode");
        }

        if(parmaKeyDict.containsKey("RspMsg"))
        {
            hRspMsg=(String)parmaKeyDict.get("RspMsg");
        }

        int hLen=hRspMsg.getBytes("GBK").length;
        int iSpace=42-hLen;
        for(int i=0;i<iSpace;i++)
        {
            hRspMsg=hRspMsg+" ";
        }


        String tranMessageHead=hTranFunc+hServType+hMacCode+hTrandateTime+hRspCode+hRspMsg+
                hConFlag+hLength+hCounterId+hThirdLogNo+hQydm;

        return tranMessageHead;
    }

    public String getReturnTranMessage_1019(HashMap parmaKeyDict) throws UnsupportedEncodingException
    {


        String custAcctId=(String)parmaKeyDict.get("CustAcctId");
        String thirdCustId=(String)parmaKeyDict.get("ThirdCustId");
        String custName=(String)parmaKeyDict.get("CustName");
        String totalAmount=(String)parmaKeyDict.get("TotalAmount");
        String totalBalance=(String)parmaKeyDict.get("TotalBalance");
        String totalFreezeAmount=(String)parmaKeyDict.get("TotalFreezeAmount");
        String tranDate=(String)parmaKeyDict.get("TranDate");

            /*组业务报文体*/
        String tranMessageBody=custAcctId+"&"+thirdCustId+"&"+custName+"&"+totalAmount+"&"+totalBalance+"&"+totalFreezeAmount+"&"+tranDate+"&&";

        return tranMessageBody;
    }
}
