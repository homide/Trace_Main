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
    ArrayList<String> allproducts = new ArrayList<String>(); // all products combine
    ArrayList<String> producturl = new ArrayList<String>();

    public void btnClick2(View view) {
        Toast.makeText(this, "Searching....", Toast.LENGTH_SHORT).show();
        if(usersearch.getText().length() <=0){
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        final String input1 = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        this.search = input1;
        listview = (ListView) findViewById(R.id.listView);

        for(int i = 0; i<5; i++){
            switch (i){
                case 1:
                    Flipkart flip = new Flipkart();
                    flip.execute("https://www.flipkart.com/search?q=" + search + "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off");
                    Paytm pyt = new Paytm();
                    pyt.execute("https://www.paytmmall.com/shop/search?q=" + search + "&from=organic&child_site_id=6");
                    Snapdeal snap = new Snapdeal();
                    snap.execute("https://www.snapdeal.com/search?keyword=" + search + "&santizedKeyword=&catId=&categoryId=0&suggested=true&vertical=&noOfResults=20&searchState=&clickSrc=suggested&lastKeyword=&prodCatId=&changeBackToAll=false&foundInAll=false&categoryIdSearched=&cityPageUrl=&categoryUrl=&url=&utmContent=&dealDetail=&sort=rlvncy");
                    break;
////                   amz.execute("https://www.amazon.in/s?k=" + search + "&ref=nb_sb_noss_2");

            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1,allproducts);
        listview.setAdapter(adapter);
        button2 = (Button) findViewById(R.id.btnSearch2);
        usersearch = (EditText) findViewById(R.id.searchText2);


    }

    private class Flipkart extends AsyncTask<String, Void, ArrayList<String>> {


        @Override
        protected void onPostExecute(ArrayList<String> s) {
            ArrayAdapter<String> adapter = null;
            String product;
            super.onPostExecute(s);
            for (int j = 0; j < 5; j++) {
                product = s.get(j);
                allproducts.add(product);
            }

        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
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
                            for (int i = 0; i < (linkArray.size()); i++) {
                                temp5 = "https://www.flipkart.com" + linkArray.get(0);
                            }
                            permanent1 = temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";
                            mainlist.add(permanent1);

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
                                permanent1 = temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";
                                mainlist.add(permanent1);
                            }
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


        @Override
        protected void onPostExecute(ArrayList<String> s) {
            String product;
            String urlstore;
            super.onPostExecute(s);
            for (int j = 0; j < 5; j++) {
                product = s.get(j);
                allproducts.add(product);
            }

//            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String[] tem = new String[5];
//                    Intent intent = null;
//                    for(int i = 0; i<5 ; i++){
//                        tem[i] = tempurlstore.get(i);
//                        intent.setData(Uri.parse(tem[i]));
//                    }
//                    intent = new Intent((Intent.ACTION_VIEW));
//                    startActivity(intent);
//                }
//            });
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
                    permanent1 = temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";
                    mainlist.add(permanent1);

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


        @Override
        protected void onPostExecute(ArrayList<String> s) {
            String product;
            super.onPostExecute(s);
            for (int j = 0; j < 5; j++) {
                product = s.get(j);
                allproducts.add(product);
            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
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
                    for (int i = 0; i < linkArray.size(); i++) {
                        temp5 = "https://www.paytmmall.com" + linkArray.get(0);
                    }
                    permanent1 = temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n";
                    mainlist.add(permanent1);

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
}
