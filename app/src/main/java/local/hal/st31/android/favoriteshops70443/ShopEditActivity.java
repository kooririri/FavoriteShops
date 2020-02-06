package local.hal.st31.android.favoriteshops70443;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShopEditActivity extends AppCompatActivity {

    private int _mode = MainActivity.MODE_INSERT;
    private long _id = 0;
    private DatabaseHelper _helper;
    private DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
    private ShopsBean shops;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_edit);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        _helper = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode",MainActivity.MODE_INSERT);
        if(_mode == MainActivity.MODE_INSERT){
            TextView tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText(R.string.tv_title_insert);

        }else{
            _id = intent.getLongExtra("id",0);
            SQLiteDatabase db = _helper.getWritableDatabase();
            shops = DataAccess.findByPk(db,_id);

            EditText etInputShopName = findViewById(R.id.etInputShopName);
            etInputShopName.setText(shops.get_name());

            EditText etInputTel = findViewById(R.id.etInputTel);
            etInputTel.setText(shops.get_tel());

            EditText etInputUrl = findViewById(R.id.etInputUrl);
            etInputUrl.setText(shops.get_url());

            EditText etInputNote = findViewById(R.id.etInputNote);
            etInputNote.setText(shops.get_note());
        }
    }



    @Override
    protected void onDestroy(){
        _helper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(_mode == MainActivity.MODE_INSERT){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.new_menu,menu);
            return true;
        }else{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_menu,menu);
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.btnSave:
                EditText etInputShopName= findViewById(R.id.etInputShopName);
                String inputShopName = etInputShopName.getText().toString();
                if(inputShopName.equals("")){
                    Toast.makeText(getApplicationContext(),R.string.msg_input_shop_name,Toast.LENGTH_SHORT).show();
                }else{
                    EditText etInputTel = findViewById(R.id.etInputTel);
                    String inputTel = etInputTel.getText().toString();

                    EditText etInputUrl = findViewById(R.id.etInputUrl);
                    String inputUrl = etInputUrl.getText().toString();

                    EditText etInputNote = findViewById(R.id.etInputNote);
                    String inputNote = etInputNote.getText().toString();

                    SQLiteDatabase db = _helper.getWritableDatabase();
                    if(_mode == MainActivity.MODE_INSERT){
                        DataAccess.insert(db,inputShopName,inputTel,inputUrl,inputNote);
                    }else{
                        DataAccess.update(db,_id,inputShopName,inputTel,inputUrl,inputNote);
                    }
                    finish();
                }
                break;

            case R.id.btnDelete:
                DeleteDialogFragment dialog = new DeleteDialogFragment();
                dialog.set_helper(_helper);
                dialog.set_id(_id);
                FragmentManager manager =getSupportFragmentManager();
                dialog.show(manager,"DeleteDialogFragment");

                break;

            case R.id.btnLink:
                String url = "";
                url = shops.get_url();
                if(!url.equals("")){
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"URLを入力してください。",Toast.LENGTH_SHORT).show();
                }
                break;

            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
