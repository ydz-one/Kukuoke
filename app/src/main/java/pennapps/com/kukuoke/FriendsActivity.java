package pennapps.com.kukuoke;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class FriendsActivity extends AppCompatActivity {

    ScrollView sv;
    Button compareBtn;
    ArrayList<CheckBox> arrCB;
    EditText friendUID;
    Button addFriendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        arrCB = new ArrayList<>();

        sv = (ScrollView) findViewById(R.id.scrollFriends);

        FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.FBU.getUid()).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sv.removeAllViews();
                LinearLayout linLayout = new LinearLayout(FriendsActivity.this);
                linLayout.setOrientation((LinearLayout.VERTICAL));

                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();

                while (itr.hasNext()) {
                    final String s = itr.next().getValue().toString();
                    final CheckBox cb = new CheckBox(FriendsActivity.this);

                    FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                String name = dataSnapshot.child(s).child("name").getValue().toString();
                                String email = dataSnapshot.child(s).child("email").getValue().toString();
                                Log.d("NAME", name);
                                Log.d("EMAIL", email);
                                cb.setText(email + " " + name);
                                cb.setTextColor(Color.GREEN);
                                arrCB.add(cb);
                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    linLayout.addView(cb);
                }

                sv.addView(linLayout);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        compareBtn = (Button) findViewById(R.id.compareBtn);
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (CheckBox cb : arrCB) {
                    if (cb.isChecked()) {
                        Log.d("CCCCCHECKBOX", cb.getText().toString());
                    }
                }
            }
        });

        friendUID = (EditText) findViewById(R.id.frienduid);
        addFriendBtn = (Button) findViewById(R.id.addFriendsBtn);
        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String frienduid = friendUID.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.FBU.getUid()).child("friends").push().setValue(frienduid);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent1 = new Intent(FriendsActivity.this, SettingsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_image:
                Intent intent2 = new Intent(FriendsActivity.this, MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_user:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
