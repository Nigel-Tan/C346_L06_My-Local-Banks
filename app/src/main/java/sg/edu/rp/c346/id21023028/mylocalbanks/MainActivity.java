package sg.edu.rp.c346.id21023028.mylocalbanks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textViewUOB;
    TextView textViewOCBC;
    TextView textViewDBS;

    private void setLanguage(int uob, int ocbc, int dbs){
        textViewOCBC.setText(ocbc);
        textViewUOB.setText(uob);
        textViewDBS.setText(dbs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDBS = findViewById(R.id.textViewDBS);
        textViewUOB = findViewById(R.id.textViewUOB);
        textViewOCBC = findViewById(R.id.textViewOCBC);

        registerForContextMenu(textViewDBS);
        registerForContextMenu(textViewOCBC);
        registerForContextMenu(textViewUOB);
    }

    //options for main menu (option menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu); //as it is options, it gives the 3 dots on the top
        return true;
    }

    //event handler for language (options menu)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId(); //determine which language used

        if (id == R.id.EnglishSelection) {
            setLanguage(R.string.uob,R.string.ocbc,R.string.dbs);
            return true;
        } else if (id == R.id.ChineseSelection) {
            setLanguage(R.string.uobChinese,R.string.ocbcChinese,R.string.dbsChinese);
            return true;
        } else { //if any error in setting language
            setLanguage(R.string.error_translation,R.string.error_translation,
                    R.string.error_translation);
        }
        return super.onOptionsItemSelected(item);
    }

    //context menu (show the menu)
    String wordClicked = ""; //used to determine which bank was clicked, help determine the info to display later
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu); //inflate method creates the menu.

        if (v == textViewDBS){
            wordClicked = "dbs";
        }
        else if (v == textViewOCBC){
            wordClicked = "ocbc";
        }
        else if (v == textViewUOB){
            wordClicked = "uob";
        }
    }

    //event handling for the menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (wordClicked.equalsIgnoreCase("uob")){ //UOB
            activityLoad(id, R.string.uobWebsite, R.string.uobNumber,textViewUOB);
            return true;
        }
        else if (wordClicked.equalsIgnoreCase("dbs")){ //DBS
            activityLoad(id, R.string.dbsWebsite, R.string.dbsNumber,textViewDBS);
            return true;
        }
        else if (wordClicked.equalsIgnoreCase("ocbc")){ //OCBC
            activityLoad(id, R.string.ocbcWebsite, R.string.ocbcNumber,textViewOCBC);
            return true;
        }
        return super.onContextItemSelected(item); //pass menu item to the superclass implementation
    }
    private void activityLoad(int id, int website, int number, TextView textView){
        if(id==R.id.websiteSelection) { //website
            //code for action
            Intent intentCall = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(website)));
            startActivity(intentCall);
        }
        else if(id==R.id.contactSelection) { //contact info
            //code for action
            Intent intentCall = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:"+getString(number)));
            startActivity(intentCall);
        }
        else if(id==R.id.toggleSelection){
            String color = String.format("#%06X", (0xFFFFFF & textView.getCurrentTextColor()));
            if (color.equalsIgnoreCase(getString(R.string.blackcolor))){
                textView.setTextColor(Color.parseColor(getString(R.string.redcolor)));
            }
            else if(color.equalsIgnoreCase(getString(R.string.redcolor))){
                textView.setTextColor(Color.parseColor(getString(R.string.blackcolor)));
            }
        }
    }
}