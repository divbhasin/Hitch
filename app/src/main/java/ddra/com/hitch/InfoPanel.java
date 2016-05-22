package ddra.com.hitch;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class InfoPanel extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_panel);

        View.OnClickListener handleClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.test1:
                        openUrl("https://en.wikipedia.org/wiki/Hydrocharis_morsus-ranae");
                        break;
                    case R.id.test2:
                        openUrl ("https://en.wikipedia.org/wiki/Impatiens_glandulifera");
                        break;
                    case R.id.test3:
                        openUrl("https://en.wikipedia.org/wiki/Diervilla_lonicera");
                        break;
                    case R.id.test4:
                        openUrl("https://en.wikipedia.org/wiki/Asian_carp");
                        break;
                    case R.id.test5:
                        openUrl("https://en.wikipedia.org/wiki/Alliaria_petiolata");
                        break;
                    case R.id.test6:
                        openUrl("https://en.wikipedia.org/wiki/Rhamnus_cathartica");
                        break;
                    case R.id.test7:
                        openUrl("https://en.wikipedia.org/wiki/Pistia");
                        break;
                    case R.id.test8:
                        openUrl("http://www.invadingspecies.com/invaders/fish/eurasian-ruffe/");
                        break;
                    case R.id.test9:
                        openUrl("https://en.wikipedia.org/wiki/Sirococcus_clavigignenti-juglandacearum");
                        break;


                }
            }
        };


        ImageView test1 = (ImageView) findViewById(R.id.test1);
        test1.setOnClickListener(handleClick);
        ImageView test2 = (ImageView) findViewById(R.id.test2);
        test2.setOnClickListener(handleClick);
        ImageView test3 = (ImageView) findViewById(R.id.test3);
        test3.setOnClickListener(handleClick);
        ImageView test4 = (ImageView) findViewById(R.id.test4);
        test4.setOnClickListener(handleClick);
        ImageView test5 = (ImageView) findViewById(R.id.test5);
        test5.setOnClickListener(handleClick);
        ImageView test6 = (ImageView) findViewById(R.id.test6);
        test6.setOnClickListener(handleClick);
        ImageView test7 = (ImageView) findViewById(R.id.test7);
        test7.setOnClickListener(handleClick);
        ImageView test8 = (ImageView) findViewById(R.id.test8);
        test8.setOnClickListener(handleClick);
        ImageView test9 = (ImageView) findViewById(R.id.test9);
        test9.setOnClickListener(handleClick);





       // ImageView b = (ImageView)findViewById(R.id.action_settings);
       // b.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         Intent intent = new Intent();
         //       intent.setAction(Intent.ACTION_VIEW);
           //     intent.addCategory(Intent.CATEGORY_BROWSABLE);
             //   intent.setData(Uri.parse("http://goo.gl/forms/wo7lnvVHzIh24nRP2"));
               // startActivity(intent);
      //      }
        //});
    }

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sendThanks();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://goo.gl/forms/wo7lnvVHzIh24nRP2"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendThanks() {
        final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

        final Map data = new HashMap();
        data.put("title", "Hitch");
        data.put("body", "Thanks for your response!");
        final JSONObject jsonData = new JSONObject(data);
        final String notificationData = new JSONArray().put(jsonData).toString();

        i.putExtra("messageType", "PEBBLE_ALERT");
        i.putExtra("sender", "PebbleKit Android");
        i.putExtra("notificationData", notificationData);
        sendBroadcast(i);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_info_panel, container, false);
            return rootView;
        }
    }
}
