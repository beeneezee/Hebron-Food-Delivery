package bee.hb.testo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HandleDatabase extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "cart.db";
    public static final String DATABASE_PATH = "/data/data/bee.hb.testo/databases/";
    private static final int DATABASE_VERSION = 1;
    private final Context dbContext;
    private SQLiteDatabase ourDatabase;

    public HandleDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.dbContext = context;
        if (checkDataBase()) {
            this.openDataBase();
            return;
        }
        try {
            this.getReadableDatabase();
            copyDataBase();
            this.close();
            this.openDataBase();
        } catch (Exception var2_2) {
            throw new Error("Error copying database");
        }
//        Toast.makeText((Context) context, (CharSequence) "", Toast.LENGTH_LONG).show();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean checkDataBase() {
        SQLiteDatabase sQLiteDatabase;
        boolean bl;
        try {
            SQLiteDatabase sQLiteDatabase2;
            sQLiteDatabase = sQLiteDatabase2 = SQLiteDatabase.openDatabase((String) ("/data/data/bee.hb.testo/databases/" + DATABASE_NAME), (SQLiteDatabase.CursorFactory) null, (int) 1);
            bl = false;
            if (sQLiteDatabase == null) return bl;
            bl = true;
        } catch (SQLiteException var1_4) {
            Log.v((String) "db log", (String) "database does't exist");
            return false;
        }
        sQLiteDatabase.close();
        return bl;
    }

    private void copyDataBase() throws IOException {
        int n;
        InputStream inputStream = this.dbContext.getAssets().open(DATABASE_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream("/data/data/bee.hb.testo/databases/" + DATABASE_NAME);
        byte[] arrby = new byte[1024];
        while ((n = inputStream.read(arrby)) > 0) {
            fileOutputStream.write(arrby, 0, n);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
    }

    public boolean checkItemInTheCart(String id) {
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, "itemid='" + id + "'", null, null, null, null);
        return cursor.getCount() > 0? true: false;
    }

    public int getItemCountInTheCart(String id) {
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"itemcount"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, "itemid='" + id + "'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return Integer.parseInt(cursor.getString(0)) - 1;
    }


    public void addToCart(String id, String name, String description, String count, String price, String picture) {
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemid", id);
        contentValues.put("itemname", name);
        contentValues.put("itemdescription", description);
        contentValues.put("itemcount", count);
        contentValues.put("itemprice", price);
        contentValues.put("itempicture", picture);
        ourDatabase.insert("items", null, contentValues);
    }

    public void deleteFromCart(String id) {
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        ourDatabase.delete("items", "itemid='" + id + "'", null);
    }

    public void deleteAllFromCart() {
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        ourDatabase.execSQL("DELETE FROM items");
    }

    public void updateItemInTheCart(String id, String count) {
        ContentValues args = new ContentValues();
        args.put("itemcount", count);
        try {
            ourDatabase.update("items", args, "itemid= '" + id +"'", null);
        }catch (Exception e){
            Toast.makeText(dbContext, e.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(dbContext, e.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(dbContext, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public String[] allIdsInTheCart(){
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String[] arrstring2 = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); ++i) {
            arrstring2[i] = cursor.getString(0);
            try {
                cursor.moveToNext();
                continue;
            } catch (Exception var5_5) {
                Toast.makeText((Context) this.dbContext, (CharSequence) var5_5.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return arrstring2;
    }

    public String[] allNamesInTheCart(){
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String[] arrstring2 = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); ++i) {
            arrstring2[i] = cursor.getString(1);
            try {
                cursor.moveToNext();
                continue;
            } catch (Exception var5_5) {
                Toast.makeText((Context) this.dbContext, (CharSequence) var5_5.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return arrstring2;
    }

    public int totalPrice(){
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int sum = 0;
        for (int i = 0; i < cursor.getCount(); ++i) {
            sum = sum + (Integer.parseInt(cursor.getString(4)) * Integer.parseInt(cursor.getString(3)));
            try {
                cursor.moveToNext();
                continue;
            } catch (Exception var5_5) {
                Toast.makeText((Context) this.dbContext, (CharSequence) var5_5.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return sum;
    }

    public String[] allDescriptionsInTheCart(){
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String[] arrstring2 = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); ++i) {
            arrstring2[i] = cursor.getString(2);
            try {
                cursor.moveToNext();
                continue;
            } catch (Exception var5_5) {
                Toast.makeText((Context) this.dbContext, (CharSequence) var5_5.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return arrstring2;
    }

    public String[] allCountsInTheCart(){
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String[] arrstring2 = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); ++i) {
            arrstring2[i] = cursor.getString(3);
            try {
                cursor.moveToNext();
                continue;
            } catch (Exception var5_5) {
                Toast.makeText((Context) this.dbContext, (CharSequence) var5_5.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return arrstring2;
    }

    public String[] allPricesInTheCart(){
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String[] arrstring2 = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); ++i) {
            arrstring2[i] = cursor.getString(4);
            try {
                cursor.moveToNext();
                continue;
            } catch (Exception var5_5) {
                Toast.makeText((Context) this.dbContext, (CharSequence) var5_5.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return arrstring2;
    }

    public String[] allPicturesInTheCart(){
        this.ourDatabase = new HandleDatabase(this.dbContext).getWritableDatabase();
        String[] arrstring = new String[]{"*"};
        Cursor cursor = this.ourDatabase.query("items", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String[] arrstring2 = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); ++i) {
            arrstring2[i] = cursor.getString(5);
            try {
                cursor.moveToNext();
                continue;
            } catch (Exception var5_5) {
                Toast.makeText((Context) this.dbContext, (CharSequence) var5_5.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return arrstring2;
    }


    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }

    public void openDataBase() throws SQLException {
        SQLiteDatabase.openDatabase((String) ("/data/data/bee.hb.testo/databases/" + DATABASE_NAME), (SQLiteDatabase.CursorFactory) null, (int) 0);
    }
}

