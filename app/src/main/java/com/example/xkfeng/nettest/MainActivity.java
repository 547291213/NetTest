package com.example.xkfeng.nettest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.xkfeng.nettest.JavaBean.SimpleClass;
import com.example.xkfeng.nettest.JavaBean.TestBean;
import com.example.xkfeng.nettest.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainAcitivy" ;

    private Button sendRequesrBtn1 ;

    private Button sendRequestBtn ;

    private Button parsingXmlBtn ;

    private Button parsingXmlBtn1 ;

    private Button parsingJSONBtn ;

    private Button parsingGSONBtn ;

    private Button parsingGsonObjectBtn ;

    private TextView responseText ;

    private BtnCliclListener cliclListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequestBtn = (Button)findViewById(R.id.sendRequesrBtn) ;
        responseText = (TextView)findViewById(R.id.responseText) ;

        cliclListener = new BtnCliclListener() ;
        sendRequestBtn.setOnClickListener(cliclListener);

        sendRequesrBtn1 = (Button)findViewById(R.id.sendRequesrBtn1) ;
        sendRequesrBtn1.setOnClickListener(cliclListener);

        parsingXmlBtn = (Button)findViewById(R.id.parsingXmlBtn) ;
        parsingXmlBtn.setOnClickListener(cliclListener);

        parsingXmlBtn1 = (Button)findViewById(R.id.parsingXmlBtn1) ;
        parsingXmlBtn1.setOnClickListener(cliclListener);

        parsingJSONBtn = (Button)findViewById(R.id.parsingJSONBtn) ;
        parsingJSONBtn.setOnClickListener(cliclListener);

        parsingGSONBtn = (Button)findViewById(R.id.parsingGSONBtn) ;
        parsingGSONBtn.setOnClickListener(cliclListener);

        parsingGsonObjectBtn = (Button)findViewById(R.id.parsingGsonObjectBtn) ;
        parsingGsonObjectBtn.setOnClickListener(cliclListener);
    }

    private class BtnCliclListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.sendRequesrBtn:
                    sendRequestWithHttpURLConnection() ;
                    break ;

                case R.id.sendRequesrBtn1:

                    sendRequestWithOkHttpClient() ;
                    break ;

                case R.id.parsingXmlBtn :
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient() ;
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2/get_data.xml")
                                    .build() ;

                            try {
                                Response response = client.newCall(request).execute() ;
                                parseXMLWithPull(response.body().string()) ;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                    break ;
                case R.id.parsingXmlBtn1 :

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient() ;
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2/get_data.xml")
                                    .build() ;
                            try {
                                Response response = client.newCall(request).execute() ;
                                parseXMLWithSax(response.body().string()) ;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                    break ;

                case R.id.parsingJSONBtn :

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient() ;
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2/get_json.json")
                                    .build() ;
                            try {
                                Response response = client.newCall(request).execute() ;
                                parseJSONWithJSONObject(response.body().string()) ;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                    break ;

                case R.id.parsingGSONBtn:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient() ;
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2/get_json.json")
                                    .build() ;
                            try {
                                Response response = client.newCall(request).execute() ;
                                List<SimpleClass> data  = (List<SimpleClass>) Utils.parseJsonArrayWithGson(response.body().string() ,SimpleClass [].class) ;
                                Log.i(TAG , "GSON IS " + data.size()
                                + "\n" + data.get(0).getId() + " " + data.get(0).getName() + " " + data.get(0).getVersion()
                                ) ;
                                Log.i(TAG ,data.toString()) ;
                                // parseJSONGson(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                    break ;
                case R.id.parsingGsonObjectBtn:

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient() ;
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2/get_data.json")
                                    .build() ;
                            try {
                                Response response = client.newCall(request).execute() ;
                             //   Log.i(TAG , "DATA IS " + response.body().string()) ;

                                TestBean bean = Utils.parseJsonObjectWithGson(response.body().string() ,TestBean.class) ;
                                Log.i(TAG , "DATA IS " + bean.getData() + bean.getId() + bean.getMsg() + "\nTEST data is " + bean.getTestData()) ;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                    break ;
                default:
                    break ;

            }

        }
    }

    private void parseJSONGson(String string)
    {

        Gson gson = new Gson() ;
        List<SimpleClass> result = gson.fromJson(string, new TypeToken<List<SimpleClass>>(){}.getType());

        for (SimpleClass simpleClass : result)
        {

            Log.d(TAG , "GSON ID " + simpleClass.getId()) ;
            Log.d(TAG , "GSON NAME " + simpleClass.getName()) ;
            Log.d(TAG , "GSON VERSION " + simpleClass.getVersion()) ;

        }

    }

    /*
    JSON解析（JSONObject）
    1 JSONArray
    2 JSONObject
    3 jsonObject.getXXX("xx") ;

     */
    private void parseJSONWithJSONObject(String string) {

        try {
            JSONArray jsonArray = new JSONArray(string) ;
            for (int i = 0 ; i < jsonArray.length() ; i++)
            {
                JSONObject object = jsonArray.getJSONObject(i) ;
                String id = object.getString("id") ;
                String name = object.getString("name") ;
                String version = object.getString("version") ;
                Log.d(TAG ,"JSON id " + id) ;
                Log.d(TAG ,"JSON name " + name) ;
                Log.d(TAG ,"JSON version " + version) ;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
      SAX  xml解析工具
      1 创建一个继承自DefaultHolder的类ContentHolder
      2 创建一个SAXParserFactory的对象
      3 获取XMLReader对象
      4 将ContentHolder实例，设置到XMLReader中
      5 调用parse方法，进行解析
     */
    private void parseXMLWithSax(String string) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance() ;
            XMLReader xmlReader = factory.newSAXParser().getXMLReader() ;
            ContentHandle handle = new ContentHandle() ;
            //将ConetntHandle的实例设置到XMLReader
            xmlReader.setContentHandler(handle);
            //开始解析
            xmlReader.parse(new InputSource(new StringReader(string)));


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
      Pull解析器的使用
      1 用XmlPullParseFactory.newInstance创建XmlPullParseFactory对象 factory
      2 用facory.newPullParse创建XmlPullParse对象parse
      3 parse.setInput(new StringReader(xmlDate))
      4 获取解析对象的类型parse.getEventType
      5 循环处理  parser.getName() 获取节点名字

     */
    private void parseXMLWithPull(String xmlDate)
    {
        try {
            XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance() ;
            XmlPullParser parser = pullParserFactory.newPullParser() ;
            parser.setInput(new StringReader(xmlDate));

            int eventType = parser.getEventType() ;
            String id ="" ;
            String name = "" ;
            String version = "" ;
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                String nodeNmae = parser.getName() ;
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeNmae))
                            id = parser.nextText() ;
                        else if ("name".equals(nodeNmae))
                            name = parser.nextText() ;
                        else if ("version".equals(nodeNmae))
                            version = parser.nextText() ;
                        break ;

                    case XmlPullParser.END_TAG :
                        if ("app".equals(nodeNmae))
                        {
                            Log.d(TAG , "id is " + id) ;

                            Log.d(TAG , "name is " + name) ;

                            Log.d(TAG , "version is " + version) ;
                        }
                        break ;

                    default:
                        break ;
                }
                eventType = parser.next() ;
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     OkHttpClien
     1 OkHttpClient client = new OkHttpClient() ;
     2 Request rquest = new Reuqest.Builder()
                        .url("xxx")
                        .build() ;
     3 Response response = clinet.newCall(request).excute() ;
     4 response.body().string()

     */
    private void sendRequestWithOkHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient() ;
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build() ;
                try {
                    Response response = client.newCall(request).execute() ;
                    showResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
        HttpURLConnection 使用流程
        1 使用URL创建对象HttpURLConnection  connection
        2 创建InputStream对象，用于获取HttpURLConnection从网络的获取的数据connection.getInputStream
        3 创建BuffereReader对象reader,转换从网络的读取的内容。
         read = new BuffereReader(new InputStreamReader(inputStream))
        4 创建StringBuilder对象response用于存储数据
        循环 String line = reader.readLine() 逐行读取。response.append(line) ;

     */
    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null ;
                BufferedReader reader = null ;
                try{
                    URL url = new URL("https:www.baidu.com") ;
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream is = connection.getInputStream() ;
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(is)) ;
                    StringBuilder response = new StringBuilder() ;
                    String line ;
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line) ;
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (reader != null)
                    {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (connection != null)
                    {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    /*
     完成内容的显示
     */
    private void showResponse(final String response)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }

    public class ContentHandle extends DefaultHandler
    {
        private String nodeName ;
        private StringBuilder id ;
        private StringBuilder name ;
        private StringBuilder version ;

        /*
              开始解析的时候调用startDocument
         */
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            id = new StringBuilder() ;
            name = new StringBuilder() ;
            version = new StringBuilder() ;
        }


        /*
        开始解析某个节点的时候调用startElement
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            //在开始解析当前节点的时候记录当前节点的名字
            nodeName = localName ;
        }

        /*
        获取节点中内容的时候调用
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

            /*
               根据当前节点的名字，决定将内容添加到哪一个StringBuilder中
             */
            if ("id".equals(nodeName))
            {
                id.append(ch , start , length) ;
            }
            else  if ( "name".equals(nodeName))
            {
                name.append(ch,start,length);
            }
            else if ("version".equals(nodeName))
            {
                version.append(ch,start,length) ;
            }

        }

        /*
        完成解析某个节点的时候调用
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if ("app".equals(localName))
            {
                Log.d(TAG , "ID IS "  + id) ;

                Log.d(TAG , "NAME IS "  + name) ;

                Log.d(TAG , "VERSION IS "  + version) ;

                id.setLength(0);
                name.setLength(0);
                version.setLength(0);
            }
        }

        /*
        完成解析整个文件时候调用
         */
        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }
}
