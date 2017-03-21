package com.prixesoft.david.ads.app;



import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static com.prixesoft.david.ads.app.R.id.myAds;
import static com.prixesoft.david.ads.app.R.id.spSubCat;

/**
 * Created by david on 09-Mar-17.
 */

public class MainActivity extends AppCompatActivity {



    private  EditText title, description, price, phone;
    private  Spinner category, subCategory ,currency;
    private  CheckBox shareFB,shareLoc;


    private  ImageButton btnImage[] =new ImageButton[6];
    private  TextView txtImage[] = new TextView[6];


    private  TextView actionBarTitle;

    private BottomBar bottomBar;
    private ImageButton btnBack;
    private LinearLayout adView;

    private String adTitle,adCat,adSubCat,adDesc,adPrice,adCurr,adPhone;
    private boolean adShareFB,adShareLoc;
    private  String  imagePath[] = new String [6];
    private  Boolean imageType[] =new Boolean[6];
    private  int imageCounter=0;

    private String layoutNames[] ={ "My Ads" , "Add Info","Add Photos" ,"Add Category" ,"Add Contacts"};
    private LinearLayout layoutPage[]= new LinearLayout[5];
    private int currentLayout,previousLayout;

    JSON json;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        // For the purpose of our app we need portrait
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Getting all the visual components that we will need
        actionBarTitle =  (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        btnBack = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.btnBack);


        layoutPage[0] = (LinearLayout) findViewById(R.id.myAds);
        currentLayout= 0; previousLayout=0;
        layoutPage[1] = (LinearLayout) findViewById(R.id.adPlace);
        layoutPage[2] = (LinearLayout) findViewById(R.id.addPhoto);
        layoutPage[3] = (LinearLayout) findViewById(R.id.addCategory);
        layoutPage[4] = (LinearLayout) findViewById(R.id.addContacts);


        title = (EditText) layoutPage[1].findViewById(R.id.txtTitle);
        description = (EditText) layoutPage[1].findViewById(R.id.txtDescription);
        price = (EditText) layoutPage[1].findViewById(R.id.txtPrice);
        currency = (Spinner) layoutPage[1].findViewById(R.id.spPrice);

        btnImage[0] = (ImageButton) layoutPage[2].findViewById(R.id.imgBtn1);
        txtImage[0] = (TextView) layoutPage[2].findViewById(R.id.txtName1);
        btnImage[1] = (ImageButton) layoutPage[2].findViewById(R.id.imgBtn2);
        txtImage[1] = (TextView) layoutPage[2].findViewById(R.id.txtName2);
        btnImage[2] = (ImageButton) layoutPage[2].findViewById(R.id.imgBtn3);
        txtImage[2] = (TextView) layoutPage[2].findViewById(R.id.txtName3);
        btnImage[3] = (ImageButton) layoutPage[2].findViewById(R.id.imgBtn4);
        txtImage[3] = (TextView) layoutPage[2].findViewById(R.id.txtName4);
        btnImage[4] = (ImageButton) layoutPage[2].findViewById(R.id.imgBtn5);
        txtImage[4] = (TextView) layoutPage[2].findViewById(R.id.txtName5);
        btnImage[5] = (ImageButton) layoutPage[2].findViewById(R.id.imgBtn6);
        txtImage[5] = (TextView) layoutPage[2].findViewById(R.id.txtName6);


        category = (Spinner) layoutPage[3].findViewById(R.id.spCat);
        subCategory = (Spinner) layoutPage[3].findViewById(spSubCat);

        phone = (EditText) layoutPage[4].findViewById(R.id.txtPhone);
        shareFB = (CheckBox) layoutPage[4].findViewById((R.id.checkFb));
        shareLoc =(CheckBox) layoutPage[4].findViewById((R.id.checkLoc));

        //Spinner spinner =category;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerCat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setPrompt("Category!");

        category.setAdapter(
                new HintSpinner(
                        adapter,
                        R.layout.hint_spinne_cat,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        MySpinner mySpinner= new MySpinner(this,category,subCategory);
        category.setOnItemSelectedListener(mySpinner);




        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                previousLayoutPage();
            }
        });

        // the bottom tab bar
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId){
                    case R.id.tab_place_ads:
                        if(currentLayout == 0 && previousLayout == 0) nextLayoutPage();

                        if(currentLayout == 0 && previousLayout != 0) {
                            currentLayout = previousLayout  ;
                            if(previousLayout > 1 )  btnBack.setVisibility(View.VISIBLE);
                                layoutPage[currentLayout].setVisibility(View.VISIBLE);
                            actionBarTitle.setText(layoutNames[currentLayout]);
                            layoutPage[0].setVisibility(View.INVISIBLE);
                            previousLayout = 0;
                        }
                        break;
                    case R.id.tab_my_ads:
                        layoutPage[currentLayout].setVisibility(View.INVISIBLE);
                        previousLayout = currentLayout;
                        layoutPage[0].setVisibility(View.VISIBLE);
                        actionBarTitle.setText(layoutNames[0]);
                        btnBack.setVisibility(View.INVISIBLE);
                        currentLayout = 0;
                        break;
                }
            }
        });

        btnImage[0] = (ImageButton) findViewById(R.id.imgBtn1);
        btnImage[1] = (ImageButton) findViewById(R.id.imgBtn2);
        btnImage[2] = (ImageButton) findViewById(R.id.imgBtn3);
        btnImage[3] = (ImageButton) findViewById(R.id.imgBtn4);
        btnImage[4] = (ImageButton) findViewById(R.id.imgBtn5);
        btnImage[5] = (ImageButton) findViewById(R.id.imgBtn6);

        // The image buttons actions mapping
        View.OnClickListener button_click = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (imageCounter < 6) {
                    final Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(galleryIntent, 1);
                }
            }
        };
        for(int i=0;i<6;i++){
            btnImage[i].setOnClickListener(button_click);
        }


        // First validation Ad Info
        Button btnAdInfo =  (Button)   layoutPage[1].findViewById(R.id.btnNext);
        btnAdInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                  validationFirst();

            }
        });
        // Second validation Photos
        Button btnPhotos =  (Button)   layoutPage[2].findViewById(R.id.btnNext);
        btnPhotos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                validationSecond();

            }
        });
        // Third validation Category
        Button btnCategory =  (Button)   layoutPage[3].findViewById(R.id.btnNext);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                validationThird();

            }
        });
        // Last validation Contacts  and release on My ads layout
        Button btnSaveAddContacts =  (Button)   layoutPage[4].findViewById(R.id.btnSave);
        btnSaveAddContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                saveInfo();


            }
        });

        // Save button on My Ads layout  // Releasing the JSON
        Button btnWriteJson =  (Button)   layoutPage[0].findViewById(R.id.btnSave);
        btnWriteJson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( adTitle == null || adTitle.equals("")) {
                    displayMsgBox( "Please click ad place button on the bottom" , "Error Nothing to Write!");
                    return;
                }
                try {
                    json= new JSON();
                    json.writeJSON(imageType, imagePath, adTitle, adCat,
                             adSubCat, adDesc, adPrice, adCurr,
                            adPhone, adShareFB , adShareLoc,
                            getFilesDir() ,MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        // Ask for permissions
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }

    }


    // Take action gallery picture selected
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        imageType[imageCounter] = true;
        Uri  uri =data.getData();
        String path= GetPath.getPath(getApplication().getApplicationContext(),uri);

        // Make thumbnail ... OpenGL crashes on pictures size 12mp++
        Drawable  thumbnail = ImageProcess.resize( Drawable.createFromPath(path) , this);

        btnImage[ imageCounter ].setBackground(thumbnail);
        btnImage[ imageCounter ].setImageResource(android.R.color.transparent);
        txtImage[ imageCounter ].setText(path.substring(path.lastIndexOf("/")+1));
        imagePath[ imageCounter ] = path;
        imageCounter++;
     }


    // Next button standard action
    void nextLayoutPage(){

        previousLayout = currentLayout++;
        layoutPage[currentLayout].setVisibility(View.VISIBLE);
        actionBarTitle.setText(layoutNames[currentLayout]);
        layoutPage[previousLayout].setVisibility(View.INVISIBLE);

    }

    // Action bar back button
    void previousLayoutPage(){

        layoutPage[currentLayout].setVisibility(View.INVISIBLE);
        previousLayout = currentLayout  ;
        currentLayout-=1;
        layoutPage[currentLayout].setVisibility(View.VISIBLE);
        actionBarTitle.setText(layoutNames[currentLayout]);

        // If user goes back to the Ad Info step
        if(currentLayout==1)  btnBack.setVisibility(View.INVISIBLE);
    }

    // First validation Ad Info
    void validationFirst() {

        //Validation of evert field and spinner
        if ( title.length() < 3) {
            title.setError(getString(R.string.error_field_required));
            // focus on the error
            title.requestFocus();
            return;
        } else if ( description.length() < 10 || description.length() > 512) {
            description.setError(getString(R.string.error_field_required));
            title.requestFocus();
            return;
        } else if ( price.getText().length() == 0 || price.length() > 8 || price.getText().toString().matches("^\\.[0-9]$")) {
            price.setError(getString(R.string.error_price));
            price.requestFocus();
            return;
        }

        nextLayoutPage();
        btnBack.setVisibility(View.VISIBLE);
    }

    // Second validation Photos
    void validationSecond() {

        if(imageCounter == 0) {
            displayMsgBox("Add at least one picture ! :/", "Problem / Error !");
            return;
        }

        nextLayoutPage();

    }

    // Third validation Category
    void validationThird() {

        if (category.getSelectedItem() == null ) {
            displayMsgBox("Select category !","Error message :/");  //trying different method for error displaying
            category.requestFocus();
            return;
        } else if (subCategory.getSelectedItem() == null) {
            displayMsgBox("Select subcategory !", "Error message :/");  //trying different method for error displaying
            subCategory.requestFocus();
            return;
        }

        nextLayoutPage();

    }

    // Last validation Contacts
    void saveInfo() {

        // Saving the information to the My Ads page
        visualiseTheAd();

        btnBack.setVisibility(View.INVISIBLE);
        layoutPage[4].setVisibility(View.INVISIBLE);
        actionBarTitle.setText(R.string.my_ads);

        bottomBar.selectTabWithId(R.id.tab_my_ads);
        layoutPage[0].setVisibility(View.VISIBLE);
        currentLayout=0; previousLayout=0;
        btnBack.setVisibility(View.INVISIBLE);
        cleanFields();

    }

    //  Release on My ads layout
    void visualiseTheAd() {
        //get scrollview add initialize new layout ad_view
        LinearLayout scroll = (LinearLayout) layoutPage[0].findViewById(R.id.scrollView);
        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ad_view, null);

        //Add layout to the scroll view
        scroll.addView(view);

        Drawable  thumbnail = ImageProcess.resize( Drawable.createFromPath(imagePath[0]) , this);

       // view.setVisibility(View.VISIBLE);
        view.findViewById(R.id.imageView).setBackground(thumbnail);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);


        //Required for writing JSON Saves requesting the text from GUI *Probably save some time
        adTitle =  title.getText().toString();
        adCat= category.getSelectedItem().toString();
        adSubCat= subCategory.getSelectedItem().toString();
        adDesc=description.getText().toString();
        adPrice= price.getText().toString();
        adCurr= currency.getSelectedItem().toString();
        adPhone = phone.getText().toString();
        adShareFB= shareFB.isChecked();
        adShareLoc= shareLoc.isChecked();

        txtTitle.setText( adTitle );
        txtPrice.setText( adPrice+ " " + adCurr );
        if(adShareLoc)
            txtLocation.setText( R.string.location ); else  txtLocation.setText( R.string.not_specified );
        txtDesc.setText( adDesc );

    }


    void cleanFields(){
        title.setText("");
        category.setSelection(0);
        subCategory.setSelection(0);
        description.setText("");
        price.setText("");
        currency.setSelection(0);
        phone.setText("");
        shareFB.setChecked(false);
        shareLoc.setChecked(false);
        Resources res = getResources();
        for(int i=imageCounter-1; i >=0 ;i--){
            try {
                btnImage[i].setBackground(Drawable.createFromXml(res,res.getXml(R.xml.btn_shape)));
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            btnImage[i].setImageResource(R.drawable.ic_photo);
            txtImage[i].setText("None");
        }

        imageCounter=0;
    }

    //Error or Success messages
    public void displayMsgBox(String msg,String title) {
        final AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setMessage(msg);
        msgBox.setTitle(title);
        msgBox.setPositiveButton(R.string.ok, null);
        msgBox.setCancelable(true);

        msgBox.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        msgBox.create().hide();
                    }
                });
        msgBox.create().show();

    }


}
