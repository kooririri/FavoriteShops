package local.hal.st31.android.favoriteshops70443;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DataAccess {
    /**
     * 全データ検索メソッド。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @return 検索結果のCursorオブジェクト。
     */
    public static Cursor findAll(SQLiteDatabase db){
        String sql = "SELECT * FROM shops";
        Cursor cursor =db.rawQuery(sql,null);
        return cursor;
    }

    /**
     * 主キーによる検索。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @return 主キーに対応するデータを格納したMemoオブジェクト。対応するデータが存在しない場合はnull。
     */
    public static ShopsBean findByPk(SQLiteDatabase db,long id){
        String sql = "SELECT * FROM shops WHERE _id = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{String.valueOf(id)});
        ShopsBean shops = null;
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String note = cursor.getString(cursor.getColumnIndex("note"));

            shops = new ShopsBean();
            shops.set_id(id);
            shops.set_name(name);
            shops.set_tel(tel);
            shops.set_url(url);
            shops.set_note(note);
        }
        return shops;
    }

    /**
     * ショップ情報を更新するメソッド。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @param name ショップ名。
     * @param tel 電話番号。
     * @param url url。
     * @param note メモ情報
     * @return 更新件数。
     */
    public static int update(SQLiteDatabase db,long id ,String name,String tel,String url,String note){
        String sql = "UPDATE shops SET name = ?, tel = ?,url = ?, note = ? WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1,name);
        stmt.bindString(2,tel);
        stmt.bindString(3,url);
        stmt.bindString(4,note);
        stmt.bindLong(5,id);
        return stmt.executeUpdateDelete();
    }

    /**
     * ショップ情報を新規登録するメソッド。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @param name ショップ名。
     * @param tel 電話番号。
     * @param url url。
     * @param note メモ情報
     * @return 登録件数。
     */
    public static long insert(SQLiteDatabase db,String name,String tel,String url,String note){
        String sql = "INSERT INTO shops(name,tel,url,note)VALUES(?,?,?,?)";
        SQLiteStatement stmt =db.compileStatement(sql);
        stmt.bindString(1,name);
        stmt.bindString(2,tel);
        stmt.bindString(3,url);
        stmt.bindString(4,note);
        return stmt.executeInsert();
    }

    /**
     * ショップ情報を削除するメソッド。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @param id ショップID。
     * @return 削除件数。
     */
    public static int delete(SQLiteDatabase db,long id){
        String sql = "DELETE FROM shops WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1,id);
        return stmt.executeUpdateDelete();
    }

}
