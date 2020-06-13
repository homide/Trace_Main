package com.kush.naya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    public ListView listview;
    public Button button2;
    public EditText usersearch;
    public String search;
    public TextView resultText;
    ArrayList<String> allproducts = new ArrayList<String>(); // all products combine
    ArrayList<String> producturl = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        listview = null;
        String input1 = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> temparraylist = (ArrayList<String>) args.getSerializable("ARRAYLIST");
        final ArrayList<String> tempurllist = (ArrayList<String>) args.getSerializable("URLLINKS");
        listview = (ListView) findViewById(R.id.listView);

        TextView resultText = (TextView) findViewById(R.id.resultText);
        resultText.setText("Showing Results for: "+input1);


        if (temparraylist.isEmpty() != true) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, temparraylist){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position, convertView, parent);
                    if (position ==0 || position == 7 || position == 14 || position == 21){
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                        String underlined = "<u>" + tv.getText() + "</u>";
                        tv.setText(Html.fromHtml(underlined));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
                    }
//                    if(position == 6 || position == 13 || position == 20|| position == 27){
//                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
//                        tv.setTextColor(Color.parseColor("#0000FF"));
//                    }
                    else{
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                    }
                    return view;
                }
            };
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String link = tempurllist.get(position);
                    Intent intent = new Intent((Intent.ACTION_VIEW));
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }
            });
        }


        button2 = (Button) findViewById(R.id.btnSearch2);
        usersearch = (EditText) findViewById(R.id.searchText2);



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usersearch.getText().length() <= 0) {
                    Toast.makeText(Main2Activity.this, "Please add something to search.", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog pd = new ProgressDialog(Main2Activity.this);
                    pd.setMessage("Searching websites...");
                    pd.show();
                    if(allproducts.size() > 0){
                        while(allproducts.size() > 0){
                            int i = 0;
                            allproducts.remove(i);
                        }
                    }
                    if(producturl.size() >0){
                        while(producturl.size() >0){
                            int i = 0;
                            producturl.remove(i);
                        }
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, allproducts){
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent){
                                    View view = super.getView(position, convertView, parent);
                                    if (position ==0 || position == 7 || position == 14 || position == 21){
                                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                                        String underlined = "<u>" + tv.getText() + "</u>";
                                        tv.setText(Html.fromHtml(underlined));
                                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
                                    }
                                    if(position == 6 || position == 13 || position == 20 || position == 27){
                                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                                        tv.setTextColor(Color.parseColor("#0000FF"));
                                    }
                                    else{
                                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                                    }
                                    return view;
                                }
                            };
                            listview.setAdapter(adapter);
                        }
                    }, 6000);

                    Thread t1 = new Thread(){
                        public void run(){
                            Flipkart flip = new Flipkart();
                            flip.execute("https://www.flipkart.com/search?q=" + usersearch.getText()+ "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off");
                        }
                    };

                    Thread t2 = new Thread(){
                        public void run(){
                            Paytm pyt = new Paytm();
                            pyt.execute("https://www.paytmmall.com/shop/search?q=" + usersearch.getText() + "&from=organic&child_site_id=6");
                        }
                    };

                    Thread t3 = new Thread(){
                        public void run(){
                            Snapdeal snap = new Snapdeal();
                            snap.execute("https://www.snapdeal.com/search?keyword=" + usersearch.getText() + "&santizedKeyword=&catId=&categoryId=0&suggested=true&vertical=&noOfResults=20&searchState=&clickSrc=suggested&lastKeyword=&prodCatId=&changeBackToAll=false&foundInAll=false&categoryIdSearched=&cityPageUrl=&categoryUrl=&url=&utmContent=&dealDetail=&sort=rlvncy");
                        }
                    };

                    Thread t4 = new Thread() {
                        public void run(){
                            Shopclues shopclues = new Shopclues();
                            shopclues.execute("https://www.shopclues.com/search?q="+usersearch.getText()+"&sc_z=2222&z=0&count=10");
                        }
                    };
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String link = producturl.get(position);
                            Intent intent = new Intent((Intent.ACTION_VIEW));
                            intent.setData(Uri.parse(link));
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    //

    private class Flipkart extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> tempurlstore = new ArrayList<>();
        String link;

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            ArrayAdapter<String> adapter = null;
            String product;
            String urlstore;
            super.onPostExecute(s);
            for (int j = 0; j < 6; j++) {
                product = s.get(j);
                urlstore = tempurlstore.get(j);
                allproducts.add(product);
                producturl.add(urlstore);
            }
            String seemore = "See more products on website....";
            allproducts.add(seemore);
            producturl.add(link);

//            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String link = tempurlstore.get(position-1);
//                    Intent intent = new Intent((Intent.ACTION_VIEW));
//                    intent.setData(Uri.parse(link));
//                    startActivity(intent);
//                }
//            });

        }

        @Override
        protected ArrayList<String> doInBackground(final String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements links = doc.getElementsByClass("_3O0U0u");
                Elements links1 = doc.getElementsByClass("_3liAhj");
                Elements fashions = doc.getElementsByClass("IIdQZO _1SSAGr");
                Elements maskssans = doc.getElementsByClass("_3liAhj");
                ArrayList<String> mainlist = new ArrayList<String>();
                mainlist.add("FLIPKART");
                link = strings[0];
                tempurlstore.add("https://www.flipkart.com");

                for (Element testlink1 : links) {
                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    Elements eltitle1 = testlink1.getElementsByClass("_3wU53n");

                    if (eltitle1.size() > 0) {
                        for (Element link : links) {

                            Elements elLink = link.getElementsByTag("a");

                            Elements eltitle2 = testlink1.getElementsByClass("_3wU53n");

                            Elements elpricebefore = link.getElementsByClass("_3auQ3N _2GcJzG");


                            Elements elpriceafter = link.getElementsByClass("_1vC4OE _2rQ-NK");


                            Elements discount = link.getElementsByClass("VGWI6T");


                            for (Element titleOfProduct : eltitle2) {
                                temp1 = "Title: " + titleOfProduct.text();

                            }

                            //product original price loop
                            for (Element priceOfProductBefore : elpricebefore) {
                                if(priceOfProductBefore == null){
                                    temp2 = "";
                                }else{
                                    temp2 = "Price before: " + priceOfProductBefore.text();
                                }
                            }

                            //product discounted price loop
                            for (Element priceOfProductAfter : elpriceafter) {
                                if(temp3 == null){
                                    temp3 = "Price: " + priceOfProductAfter.text();
                                }else{
                                    temp3 = "Discounted price: " + priceOfProductAfter.text();
                                }

                            }

                            //discount in number loop
                            for (Element productdiscount : discount) {
                                if(discount == null){
                                    temp4 = "";
                                }else{
                                    temp4 = "Discount: " + productdiscount.text();
                                }

                            }

                            ArrayList<String> linkArray = new ArrayList<String>();
                            for (Element elementLink : elLink) {
                                String MainLink = elementLink.attr("href");
                                linkArray.add(MainLink);
                            }
                            for (int i = 0; i < (linkArray.size()); i++) {
                                temp5 = "https://www.flipkart.com" + linkArray.get(0);
                            }

                            if (elpricebefore.text()==null)
                            {
                                permanent1 = "\n" + temp1 + "\n" + "Price :" + elpriceafter.text() + "\n" + temp4 + "\n";
                            }

                            else
                            {
                                permanent1 ="\n" +  temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";

                            }
                            mainlist.add(permanent1);
                            tempurlstore.add(temp5);
                        }
                    }
                }

                for (Element testlink2 : links1) {
                    Elements Testrun = testlink2.getElementsByClass("_1rcHFq");
                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    if (Testrun.size() > 0) {

                        for (Element link1 : links1) {
                            Elements elLink1 = link1.getElementsByTag("a");

                            Elements eltitle2 = link1.getElementsByClass("_2cLu-l");


                            Elements elpricebefore1 = link1.getElementsByClass("_1vC4OE");


                            Elements elpriceafter1 = link1.getElementsByClass("_3auQ3N");

                            Elements discount1 = link1.getElementsByClass("VGWI6T");


                            //product title loop
                            if (eltitle2.size() > 0) {
                                for (Element titleOfProduct : eltitle2) {
                                    temp1 = "Title: " + titleOfProduct.text();

                                }

                                //product original price loop
                                for (Element priceOfProductBefore : elpricebefore1) {
                                    temp2 = "Price before: " + priceOfProductBefore.text();
                                }

                                //product discounted price loop
                                for (Element priceOfProductAfter : elpriceafter1) {
                                    temp3 = "Discounted price: " + priceOfProductAfter.text();
                                }

                                //discount in number loop
                                for (Element productdiscount : discount1) {
                                    temp4 = "Discount: " + productdiscount.text();
                                }

                                ArrayList<String> linkArray = new ArrayList<String>();
                                for (Element elementLink : elLink1) {
                                    String MainLink = elementLink.attr("href");
                                    linkArray.add(MainLink);
                                }
                                for (int i = 0; i < 1; i++) {
                                    temp5 = "https://www.flipkart.com" + linkArray.get(0);
                                }


                                if (elpricebefore1.text()==null)
                                {
                                    permanent1 = "\n" + temp1 + "\n" + "Price :" + elpriceafter1.text() + "\n" + temp4 + "\n";
                                }

                                else
                                {
                                    permanent1 ="\n" +  temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";

                                }
                                mainlist.add(permanent1);
                                tempurlstore.add(temp5);
                            }
                        }
                    }
                }

                for (Element fashion : fashions) {
                    //BatchUpdates
                    Elements fashiontitle = fashion.getElementsByClass("_2mylT6");

                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    if (fashiontitle.size() > 0) {
                        for (Element fash : fashions) {

                            Elements flink = fash.getElementsByTag("a");

                            Elements fashiontitlem = fashion.getElementsByClass("_2mylT6");

                            Elements fpricebefore = fash.getElementsByClass("_3auQ3N");


                            Elements fpriceafter = fash.getElementsByClass("_1vC4OE");


                            Elements fdiscount = fash.getElementsByClass("VGWI6T");


                            for (Element ftitle : fashiontitlem) {
                                temp1 = "Title: " + ftitle.text();

                            }

                            //product original price loop
                            for (Element fproductpricebefore : fpricebefore) {
                                temp2 = "Price before: " + fproductpricebefore.text();
                            }

                            //product discounted price loop
                            for (Element fproductproceafter : fpriceafter) {
                                temp3 = "Discounted price: " + fproductproceafter.text();

                            }

                            //discount in number loop
                            for (Element fproductdiscount : fdiscount) {
                                temp4 = "Discount: " + fproductdiscount.text();

                            }

                            ArrayList<String> linkArray = new ArrayList<String>();
                            for (Element felementLink : flink) {
                                String fMainLink = felementLink.attr("href");
                                linkArray.add(fMainLink);
                            }
                            for (int i = 0; i < (linkArray.size()); i++) {
                                temp5 = "https://www.flipkart.com" + linkArray.get(0);
                            }

                            if (fpricebefore.text()==null)
                            {
                                permanent1 = "\n" + temp1 + "\n" + "Price :" + fpriceafter.text() + "\n" + temp4 + "\n";
                            }

                            else
                            {
                                permanent1 ="\n" +  temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";

                            }
                            mainlist.add(permanent1);
                            tempurlstore.add(temp5);
                        }

                    }

                }

                for (Element maska : maskssans) {
                    Elements masktitle = maska.getElementsByClass("_2cLu-l");

                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    if (masktitle.size() > 0) {
                        for (Element mask : maskssans) {

                            Elements mlink = mask.getElementsByTag("a");

                            Elements masktitle1 = maska.getElementsByClass("_2cLu-l");


                            Elements mpricebefore = mask.getElementsByClass("_3auQ3N");


                            Elements mpriceafter = mask.getElementsByClass("_1vC4OE");


                            Elements mdiscount = mask.getElementsByClass("VGWI6T");

                            for (Element mtitle : masktitle1) {
                                temp1 = "Title: " + mtitle.text();

                            }

                            //product original price loop
                            for (Element mproductpricebefore : mpricebefore) {
                                temp2 = "Price before: " + mproductpricebefore.text();
                            }

                            //product discounted price loop
                            for (Element mproductproceafter : mpriceafter) {
                                temp3 = "Discounted price: " + mproductproceafter.text();

                            }

                            //discount in number loop
                            for (Element mproductdiscount : mdiscount) {
                                temp4 = "Discount: " + mproductdiscount.text();

                            }

                            ArrayList<String> linkArray = new ArrayList<String>();
                            for (Element melementLink : mlink) {
                                String fMainLink = melementLink.attr("href");
                                linkArray.add(fMainLink);
                            }
                            for (int i = 0; i < (linkArray.size()); i++) {
                                temp5 = "https://www.flipkart.com" + linkArray.get(0);
                            }

                            if (mpricebefore.text()==null)
                            {
                                permanent1 = "\n" + temp1 + "\n" + "Price :" + mpriceafter.text() + "\n" + temp4 + "\n";
                            }

                            else
                            {
                                permanent1 ="\n" +  temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";

                            }
                            mainlist.add(permanent1);
                            tempurlstore.add(temp5);

                        }
                    }

                }
                return mainlist;
            } catch (Exception e) {
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }

    private class Snapdeal extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> tempurlstore = new ArrayList<>();
        String link;

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            String product;
            String urlstore;
            super.onPostExecute(s);

            for (int j = 0; j < 6; j++) {
                product = s.get(j);
                urlstore = tempurlstore.get(j);
                allproducts.add(product);
                producturl.add(urlstore);
            }
            String seemore = "See more products on website....";
            allproducts.add(seemore);
            producturl.add(link);

        }


        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements links = doc.getElementsByClass("col-xs-6  favDp product-tuple-listing js-tuple ");
                ArrayList<String> mainlist = new ArrayList<String>();
                mainlist.add("SNAPDEAL");
                link = strings[0];
                tempurlstore.add("https://www.snapdeal.com");


                for (Element link : links) {
                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    Elements elLink = link.getElementsByTag("a");

                    Elements eltitle = link.getElementsByClass("product-title"); //for product title

                    Elements elpricebefore = link.getElementsByClass("lfloat product-desc-price strike ");

                    Elements elpriceafter = link.getElementsByClass("lfloat product-price");

                    Elements discount = link.getElementsByClass("product-discount");


                    //product title loop
                    for (Element titleOfProduct : eltitle) {
                        temp1 = "Title: " + titleOfProduct.text();
                    }

                    //product original price loop
                    for (Element priceOfProductBefore : elpricebefore) {
                        temp2 = "Price before: " + priceOfProductBefore.text();
                    }

                    //product discounted price loop
                    for (Element priceOfProductAfter : elpriceafter) {
                        temp3 = "Discounted price: " + priceOfProductAfter.text();
                    }

                    //discount in number loop
                    for (Element productdiscount : discount) {
                        temp4 = "Discount: " + productdiscount.text();
                    }

                    ArrayList<String> linkArray = new ArrayList<String>();
                    for (Element elementLink : elLink) {
                        String MainLink = elementLink.attr("href");
                        linkArray.add(MainLink);
                    }

                    for (int j = 0; j < 1; j++) {
                        temp5 = linkArray.get(0);
                    }

                    if (elpricebefore.text()==null)
                    {
                        permanent1 = "\n" + temp1 + "\n" + "Price :" + elpriceafter.text() + "\n" + temp4 + "\n";
                    }

                    else
                    {
                        permanent1 ="\n" +  temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";

                    }
                    mainlist.add(permanent1);
                    tempurlstore.add(temp5);

                }
                return mainlist;
            } catch (Exception e) {
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }

    public class Paytm extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> tempurlstore = new ArrayList<>();
        String link;

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            String product;
            String urlstore;
            super.onPostExecute(s);
            for (int j = 0; j < 6; j++) {
                product = s.get(j);
                urlstore = tempurlstore.get(j);
                allproducts.add(product);
                producturl.add(urlstore);
            }
            String seemore = "See more products on website....";
            allproducts.add(seemore);
            producturl.add(link);
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements links = doc.getElementsByClass("_3WhJ");
                ArrayList<String> mainlist = new ArrayList<String>();
                mainlist.add("PAYTM");
                link = strings[0];
                tempurlstore.add("https://www.paytmmall.com");

                for (Element link : links) {
                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    Elements elLink = link.getElementsByTag("a");

                    Elements eltitle = link.getElementsByClass("UGUy"); //for product title

                    Elements elpricebefore = link.getElementsByClass("dQm2");

                    Elements elpriceafter = link.getElementsByClass("_1kMS");

                    Elements discount = link.getElementsByClass("c-ax");


                    //product title loop
                    for (Element titleOfProduct : eltitle) {
                        temp1 = "Title: " + titleOfProduct.text();
                    }

                    //product original price loop
                    for (Element priceOfProductBefore : elpricebefore) {
                        String s1 = priceOfProductBefore.text();
                        char[] a1 = s1.toCharArray();
                        String new1 = "₹ ";
                        int diff = (a1.length)-3;

                        for (int x = 0; x<diff-1; x++){
                            new1 = new1 + a1[x];
                        }
                        temp2 = "Price before: " + new1;
                    }

                    //product discounted price loop
                    for (Element priceOfProductAfter : elpriceafter) {
                        temp3 = "Discounted price: " + priceOfProductAfter.text();
                    }

                    //discount in number loop
                    for (Element productdiscount : discount) {
                        temp4 = "Discount: " + productdiscount.text();
                    }

                    ArrayList<String> linkArray = new ArrayList<String>();
                    for (Element elementLink : elLink) {
                        String MainLink = elementLink.attr("href");
                        linkArray.add(MainLink);
                    }
                    for (int i = 0; i < linkArray.size(); i++) {
                        temp5 = "https://www.paytmmall.com" + linkArray.get(0);
                    }

                    if (elpricebefore.text()==null)
                    {
                        permanent1 = "\n" + temp1 + "\n" + "Price :" + elpriceafter.text() + "\n" + temp4 + "\n";
                    }

                    else
                    {
                        permanent1 ="\n" +  temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";

                    }
                    mainlist.add(permanent1);
                    tempurlstore.add(temp5);

                }
                return mainlist;
            } catch (Exception e) {
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }

    private class Shopclues extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> tempurlstore = new ArrayList<>();
        String link;

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            String product;
            String urlstore;
            super.onPostExecute(s);
            for (int j = 0; j < 6; j++) {
                product = s.get(j);
                urlstore = tempurlstore.get(j);
                allproducts.add(product);
                producturl.add(urlstore);
            }
            String seemore = "See more products on website....";
            allproducts.add(seemore);
            producturl.add(link);

        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements links = doc.getElementsByClass("column col3 search_blocks");
                ArrayList<String> mainlist = new ArrayList<String>();
                mainlist.add("SHOPCLUES");
                link = strings[0];
                tempurlstore.add("https://www.shopclues.com");


                for (Element link : links) {
                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    Elements elLink = link.getElementsByTag("a");

                    Elements eltitle = link.getElementsByTag("h2"); //for product title

                    Elements elpricebefore = link.getElementsByClass("old_prices");

                    Elements elpriceafter = link.getElementsByClass("p_price");

                    Elements discount = link.getElementsByClass("prd_discount");


                    //product title loop
                    for (Element titleOfProduct : eltitle) {
                        temp1 = "Title: " + titleOfProduct.text();
                    }

                    //product original price loop
                    for (Element priceOfProductBefore : elpricebefore) {
                        temp2 = "Price before: " + priceOfProductBefore.text();
                    }

                    //product discounted price loop
                    for (Element priceOfProductAfter : elpriceafter) {
                        temp3 = "Discounted price: " + priceOfProductAfter.text();
                    }

                    //discount in number loop
                    for (Element productdiscount : discount) {
                        temp4 = "Discount: " + productdiscount.text();
                    }

                    ArrayList<String> linkArray = new ArrayList<String>();
                    for (Element elementLink : elLink) {
                        String MainLink = elementLink.attr("href");
                        linkArray.add(MainLink);
                    }

                    for (int j = 0; j < 1; j++) {
                        temp5 = "https:" + linkArray.get(0);
                    }

                    if (elpricebefore.text()==null)
                    {
                        permanent1 = "\n" + temp1 + "\n" + "Price :" + elpriceafter.text() + "\n" + temp4 + "\n";
                    }

                    else
                    {
                        permanent1 ="\n" +  temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";

                    }
                    mainlist.add(permanent1);
                    tempurlstore.add(temp5);

                }
                return mainlist;
            } catch (Exception e) {
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
//        Intent categoryIntent = new Intent (Main2Activity.this, MainActivity.class);
//        startActivity(categoryIntent);
//        finish();
        startActivity(new Intent(Main2Activity.this,MainActivity.class));
        finish();
    }
}
