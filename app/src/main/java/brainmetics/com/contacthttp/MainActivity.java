package brainmetics.com.contacthttp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import brainmetics.com.contacthttp.adapter.ContactAdapter;
import brainmetics.com.contacthttp.api.APIClient;
import brainmetics.com.contacthttp.api.ContactPersonInterface;
import brainmetics.com.contacthttp.domain.ContactPerson;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listContact)
    ListView listContact;

    List<ContactPerson> contacts = new ArrayList<ContactPerson>();

    ContactPersonInterface contactPersonInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contactPersonInterface =
                APIClient.getClient().create(ContactPersonInterface.class);
        registerForContextMenu(listContact);
    }

    private void loadAllContact(){
        final ProgressDialog pd = ProgressDialog.show(this,"","Loading..",false);
        Call<List<ContactPerson>> call = contactPersonInterface.findAll();
        call.enqueue(new Callback<List<ContactPerson>>() {
            @Override
            public void onResponse(Call<List<ContactPerson>> call,
                                   Response<List<ContactPerson>> response) {
                contacts = response.body();
                listContact.setAdapter
                        (new ContactAdapter(MainActivity.this,contacts));
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<List<ContactPerson>> call,
                                  Throwable t) {
                pd.dismiss();
                Log.e("ERROR",t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllContact();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuAdd:
                Intent intent = new Intent(this,InputActivity.class);
                startActivity(intent);
                break;
            default: break;
        }
        return true;
    }
}
