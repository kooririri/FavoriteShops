package local.hal.st31.android.favoriteshops70443;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    static final int MODE_INSERT = 1;
    static final int MODE_EDIT = 2;
    private ListView _lvMemoList;
    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _lvMemoList = findViewById(R.id.lvShopList);
        _lvMemoList.setOnItemClickListener(new ListItemClickListener());
        _helper = new DatabaseHelper(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = _helper.getWritableDatabase();
        Cursor cursor = DataAccess.findAll(db);
        String[] from = {"name"};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,cursor,from,to,0);
        _lvMemoList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(),ShopEditActivity.class);
            intent.putExtra("mode",MODE_EDIT);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btnNew){
            Intent intent = new Intent(getApplicationContext(),ShopEditActivity.class);
            intent.putExtra("mode",MODE_INSERT);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
