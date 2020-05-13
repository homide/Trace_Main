package com.kush.naya;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;


public class Main2Activity extends AppCompatActivity {


    public ListView listview;
    public Button button2;
    public EditText usersearch;
    public String searchmain;


    public void btnClick2(View view) {
        Toast.makeText(this, "Searching....", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        listview = (ListView) findViewById(R.id.listView);
        button2 = (Button) findViewById(R.id.btnSearch2);
        usersearch = (EditText) findViewById(R.id.btnClick2);




        View.OnClickListener view = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<5; i++){
                    switch (i){
//                        case 1:
//                            Flipkart flip = new Flipkart();
//                            flip.execute("https://www.flipkart.com/search?q=" +usersearch.getText()+ "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off");
//                            break;

//                        case 2:
//                            Snapdeal snap = new Snapdeal();
//                            snap.execute("https://www.snapdeal.com/search?keyword=" + usersearch.getText() + "&santizedKeyword=&catId=&categoryId=0&suggested=true&vertical=&noOfResults=20&searchState=&clickSrc=suggested&lastKeyword=&prodCatId=&changeBackToAll=false&foundInAll=false&categoryIdSearched=&cityPageUrl=&categoryUrl=&url=&utmContent=&dealDetail=&sort=rlvncy");
//                            break;

                        case 3:
                            Paytm pyt = new Paytm();
                            pyt.execute("https://www.paytmmall.com/shop/search?q=" + usersearch.getText() + "&from=organic&child_site_id=6");
                            break;

                    }
                }

            }
        };
        button2.setOnClickListener(view);


    }
    private class Snapdeal extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected void onPostExecute(ArrayList<String> s) {
            ArrayAdapter<String> adapter = null;
            String product = null;
            ArrayList<String> tempArray;
            super.onPostExecute(s);
            for(int i = 0; i <1; i++){
                tempArray = new ArrayList<>(Arrays.asList(product));
                adapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_list_item_1,s);
                listview.setAdapter(adapter);
                break;

            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements links = doc.getElementsByClass("col-xs-6  favDp product-tuple-listing js-tuple ");
                ArrayList<String> mainlist = new ArrayList<String>();


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
                        temp1 ="Title: " + titleOfProduct.text();
                    }

                    //product original price loop
                    for (Element priceOfProductBefore : elpricebefore) {
                        temp2 = "Price before: " + priceOfProductBefore.text();
                    }

                    //product discounted price loop
                    for (Element priceOfProductAfter : elpriceafter) {
                        temp3 ="Discounted price: " + priceOfProductAfter.text();
                    }

                    //discount in number loop
                    for (Element productdiscount : discount) {
                        temp4 ="Discount: " +  productdiscount.text();
                    }

                    ArrayList<String> linkArray = new ArrayList<String>();
                    for (Element elementLink : elLink) {
                        String MainLink = elementLink.attr("href");
                        linkArray.add(MainLink);
                    }

                    for (int j = 0; j < 1; j++) {
                        temp5 = linkArray.get(0);
                    }
                    permanent1 = temp1 +"\n" + temp2 +"\n" + temp3 + "\n" + temp4 +"\n" + temp5 +"\n";
                    mainlist.add(permanent1);
                }
                return mainlist;
            } catch (Exception e){
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }


    private class Flipkart extends AsyncTask<String, Void, ArrayList<String>>{
        @Override
        protected void onPostExecute(ArrayList<String> s) {
            ArrayAdapter<String> adapter = null;
            String product = null;
            ArrayList<String> tempArray;
            super.onPostExecute(s);
            for(int i = 0; i <1; i++){
                tempArray = new ArrayList<>(Arrays.asList(product));
                adapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_list_item_1,s);
                listview.setAdapter(adapter);
                break;

            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try{
                Document doc = Jsoup.connect(strings[0]).get();
                //col-xs-6  favDp product-tuple-listing js-tuple
                Elements links = doc.getElementsByClass("_3O0U0u");
                Elements links1 = doc.getElementsByClass("_3liAhj");
                ArrayList<String> mainlist = new ArrayList<String>();

                for (Element testlink1 : links) {
                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    Elements eltitle1 = testlink1.getElementsByClass("_3wU53n");

                    if (eltitle1.size() > 0) {
                        for (Element link : links) {

                            Elements elLink = link.getElementsByTag("a");

                            Elements elpricebefore = link.getElementsByClass("_3auQ3N _2GcJzG");


                            Elements elpriceafter = link.getElementsByClass("_1vC4OE _2rQ-NK");


                            Elements discount = link.getElementsByClass("VGWI6T");


                            for (Element titleOfProduct : eltitle1) {
                                temp1 ="Title: " + titleOfProduct.text();

                            }

                            //product original price loop
                            for (Element priceOfProductBefore : elpricebefore) {
                                temp2 = "Price before: " + priceOfProductBefore.text();
                            }

                            //product discounted price loop
                            for (Element priceOfProductAfter : elpriceafter) {
                                temp3 ="Discounted price: " + priceOfProductAfter.text();

                            }

                            //discount in number loop
                            for (Element productdiscount : discount) {
                                temp4 ="Discount: " +  productdiscount.text();

                            }

                            ArrayList<String> linkArray = new ArrayList<String>();
                            for (Element elementLink : elLink) {
                                String MainLink = elementLink.attr("href");
                                linkArray.add(MainLink);
                            }
                            for (int i = 0; i < (linkArray.size()); i++) {
                                temp5 = linkArray.get(0);
                            }
                            permanent1 = temp1 +"\n" + temp2 +"\n" + temp3 + "\n" + temp4 +"\n" + temp5 +"\n";
                            mainlist.add(permanent1);

                        }
                    }
                    break;
                }

                for(Element testlink2 : links1){
                    Elements Testrun = testlink2.getElementsByClass("_1rcHFq");
                    String temp1 = null, temp2 = null, temp3 = null, temp4 = null, temp5 = null;
                    String permanent1 = null;

                    if(Testrun.size() > 0){

                        for (Element link1 : links1) {
                            Elements elLink1 = link1.getElementsByTag("a");

                            Elements eltitle2 = link1.getElementsByClass("_2cLu-l");


                            Elements elpricebefore1 = link1.getElementsByClass("_1vC4OE");


                            Elements elpriceafter1 = link1.getElementsByClass("_3auQ3N");

                            Elements discount1 = link1.getElementsByClass("VGWI6T");


                            //product title loop
                            if (eltitle2.size() > 0) {
                                for (Element titleOfProduct : eltitle2) {
                                    temp1 ="Title: " + titleOfProduct.text();

                                }

                                //product original price loop
                                for (Element priceOfProductBefore : elpricebefore1) {
                                    temp2 = "Price before: " + priceOfProductBefore.text();
                                }

                                //product discounted price loop
                                for (Element priceOfProductAfter : elpriceafter1) {
                                    temp3 ="Discounted price: " + priceOfProductAfter.text();
                                }

                                //discount in number loop
                                for (Element productdiscount : discount1) {
                                    temp4 ="Discount: " +  productdiscount.text();
                                }

                                ArrayList<String> linkArray = new ArrayList<String>();
                                for (Element elementLink : elLink1) {
                                    String MainLink = elementLink.attr("href");
                                    linkArray.add(MainLink);
                                }
                                for (int i = 0; i < 1 ; i++) {
                                    temp5 = linkArray.get(0);
                                }
                                permanent1 = temp1 +"\n" + temp2 +"\n" + temp3 + "\n" + temp4 +"\n" + temp5 +"\n";
                                mainlist.add(permanent1);
                            }
                        }
                    }
                    break;
                }
                return mainlist;
            } catch (Exception e){
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }


    private class Amazon extends AsyncTask<String, Void, ArrayList<String>>{
        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            for(int i = 0; i <5; i++){
                String product = s.get(i);
                ArrayList<String> tempArray = new ArrayList<>(Arrays.asList(product));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_list_item_1,tempArray);
                listview.setAdapter(adapter);
            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements links = doc.getElementsByClass("col-xs-6  favDp product-tuple-listing js-tuple ");
                ArrayList<String> mainlist = new ArrayList<String>();


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
                        temp1 ="Title: " + titleOfProduct.text();
                    }

                    //product original price loop
                    for (Element priceOfProductBefore : elpricebefore) {
                        temp2 = "Price before: " + priceOfProductBefore.text();
                    }

                    //product discounted price loop
                    for (Element priceOfProductAfter : elpriceafter) {
                        temp3 ="Discounted price: " + priceOfProductAfter.text();
                    }

                    //discount in number loop
                    for (Element productdiscount : discount) {
                        temp4 ="Discount: " +  productdiscount.text();
                    }

                    ArrayList<String> linkArray = new ArrayList<String>();
                    for (Element elementLink : elLink) {
                        String MainLink = elementLink.attr("href");
                        linkArray.add(MainLink);
                    }

                    for (int j = 0; j < 1; j++) {
                        temp5 = linkArray.get(0);
                    }
                    permanent1 = temp1 +"\n" + temp2 +"\n" + temp3 + "\n" + temp4 +"\n" + temp5 +"\n";
                    mainlist.add(permanent1);
                }
                return mainlist;
            } catch (Exception e){
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }


    private class Paytm extends AsyncTask<String, Void, ArrayList<String>>{
        @Override
        protected void onPostExecute(ArrayList<String> s) {
            ArrayAdapter<String> adapter = null;
            String product = null;
            ArrayList<String> runArray = new ArrayList<>();
            super.onPostExecute(s);
            for(int j = 0; j <5; j++){
                product= s.get(j);
                runArray.add(j,product);
            }
            String productstring = null;
            ArrayList<String> tempArray = new ArrayList<>(Arrays.asList(productstring));
            for(int k = 0; k<5; k++){
                productstring = runArray.get(k);
                tempArray.add(productstring);
            }
            for(int i = 0; i <5; i++){
                adapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_list_item_1,tempArray);
                listview.setAdapter(adapter);
                break;

            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            //this is just a check for git
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Elements links = doc.getElementsByClass("_3WhJ");
                ArrayList<String> mainlist = new ArrayList<String>();

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
                        temp1 ="Title: " + titleOfProduct.text();
                    }

                    //product original price loop
                    for (Element priceOfProductBefore : elpricebefore) {
                        temp2 = "Price before: " + priceOfProductBefore.text();
                    }

                    //product discounted price loop
                    for (Element priceOfProductAfter : elpriceafter) {
                        temp3 ="Discounted price: " + priceOfProductAfter.text();
                    }

                    //discount in number loop
                    for (Element productdiscount : discount) {
                        temp4 ="Discount: " +  productdiscount.text();
                    }

                    ArrayList<String> linkArray = new ArrayList<String>();
                    for (Element elementLink : elLink) {
                        String MainLink = elementLink.attr("href");
                        linkArray.add(MainLink);
                    }
                    for (int i = 0; i < linkArray.size(); i++) {
                        temp5 = "https://www.paytmmall.com" + linkArray.get(0);
                    }
                    permanent1 = temp1 +"\n" + temp2 +"\n" + temp3 + "\n" + temp4 +"\n" + temp5 +"\n";
                    mainlist.add(permanent1);

                }return mainlist;
                }catch (Exception e){
                ArrayList<String> exception = new ArrayList<String>();
                String ex = e.toString();
                exception.add(ex);
                return exception;
            }
        }
    }
}
