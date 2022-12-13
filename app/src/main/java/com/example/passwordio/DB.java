package com.example.passwordio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.passwordio.models.Folder;
import com.example.passwordio.models.Login;
import com.example.passwordio.models.Note;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Passwordio.db";

    public static final String TABLE_FOLDER_NAME = "FOLDER";
    public static final String TABLE_LOGIN_NAME = "LOGIN";
    public static final String TABLE_NOTE_NAME = "NOTE";

    public static final String COL_ID = "ID";

    public static final String COL_FOLDER_NAME = "FOLDER_NAME";

    public static final String COL_LOGIN_USERNAME = "USERNAME";
    public static final String COL_LOGIN_PASSWORD = "PASSWORD";
    public static final String COL_LOGIN_URL = "URL";
    public static final String COL_LOGIN_FOLDER_ID = "FOLDER_ID";

    public static final String COL_NOTE_NAME = "NOTE_NAME";
    public static final String COL_NOTE_TEXT = "NOTE_TEXT";
    public static final String COL_NOTE_FOLDER_ID = "FOLDER_ID";


    private static final String TASK_TABLE_FOLDER_CREATE = "create table "
            + TABLE_FOLDER_NAME + " ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_FOLDER_NAME + " text not null UNIQUE"
            + ");";

    private static final String TASK_TABLE_LOGIN_CREATE = "create table "
            + TABLE_LOGIN_NAME + " ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_LOGIN_USERNAME + " text not null,"
            + COL_LOGIN_PASSWORD + " text not null,"
            + COL_LOGIN_URL + " text not null,"
            + COL_LOGIN_FOLDER_ID + " integer,"
            + " FOREIGN KEY ("+COL_LOGIN_FOLDER_ID+") REFERENCES "+TABLE_FOLDER_NAME+"("+COL_ID+")"
            + " ON DELETE SET NULL "
            + ");";

    private static final String TASK_TABLE_NOTE_CREATE = "create table "
            + TABLE_NOTE_NAME + " ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_NOTE_NAME + " text not null UNIQUE,"
            + COL_NOTE_TEXT + " text not null,"
            + COL_NOTE_FOLDER_ID + " integer,"
            + " FOREIGN KEY ("+COL_NOTE_FOLDER_ID+") REFERENCES "+TABLE_FOLDER_NAME+"("+COL_ID+")"
            + " ON DELETE SET NULL "
            + ");";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TASK_TABLE_FOLDER_CREATE);
        sqLiteDatabase.execSQL(TASK_TABLE_LOGIN_CREATE);
        sqLiteDatabase.execSQL(TASK_TABLE_NOTE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLDER_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Folder[] allFolders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_FOLDER_NAME+" ORDER BY "+COL_FOLDER_NAME,null);
        Folder[] result = new Folder[res.getCount()];
        int i = 0;
        while (res.moveToNext()) {
            long id = res.getInt(0);
            long count = folderCount(db, id);
            result[i] = new Folder(id, res.getString(1), count);
            i++;
        }
        return result;
    }

    public long createFolder(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FOLDER_NAME, name);
        long id =  db.insert(TABLE_FOLDER_NAME, null, contentValues);
        db.close();
        return id;
    }

    public void updateFolder(Folder folder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FOLDER_NAME, folder.name);
        db.update(TABLE_FOLDER_NAME, contentValues, COL_ID+"=?", new String[]{String.valueOf(folder.id)});
        db.close();
    }

    public void deleteFolder(Folder folder){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOLDER_NAME, COL_ID+"=?", new String[]{String.valueOf(folder.id)});
        db.close();
    }

    public Login[] allLogins(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_LOGIN_NAME+" ORDER BY "+COL_LOGIN_URL,null);
        Login[] result = new Login[res.getCount()];
        int i = 0;
        while (res.moveToNext()) {
            result[i] = new Login(res.getLong(0), res.getString(1), res.getString(2), res.getString(3));
            i++;
        }
        return result;
    }

    public Login[] loginsByFolder(long folderID){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql;
        if (folderID < 1) {
            sql = "select * from "+TABLE_LOGIN_NAME+" WHERE "+COL_LOGIN_FOLDER_ID+" IS NULL ORDER BY "+COL_LOGIN_URL;
        } else {
            sql = "select * from "+TABLE_LOGIN_NAME+" WHERE "+COL_LOGIN_FOLDER_ID+" = "+folderID+" ORDER BY "+COL_LOGIN_URL;
        }
        Cursor res = db.rawQuery(sql,null);
        Login[] result = new Login[res.getCount()];
        int i = 0;
        while (res.moveToNext()) {
            result[i] = new Login(res.getLong(0), res.getString(1), res.getString(2), res.getString(3));
            i++;
        }
        return result;
    }

    public Login loginsByID(long loginID){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from "+TABLE_LOGIN_NAME+" WHERE "+COL_ID+" = "+loginID;
        Cursor res = db.rawQuery(sql,null);
        if (res.moveToNext()) {
            return new Login(res.getLong(0), res.getString(1), res.getString(2), res.getString(3));
        }
        return new Login(-1, "", "", "");
    }

    public long createLogin(String username, String password, String url){
        return createLogin(username, password, url, -1);
    }

    public long createLogin(String username, String password, String url, long folder_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_LOGIN_USERNAME, username);
        contentValues.put(COL_LOGIN_PASSWORD, password);
        contentValues.put(COL_LOGIN_URL, url);
        if (folder_id > 0) {
            contentValues.put(COL_LOGIN_FOLDER_ID, folder_id);
        }
        long id = db.insert(TABLE_LOGIN_NAME, null, contentValues);
        db.close();
        return id;
    }

    public void updateLogin(Login login) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_LOGIN_USERNAME, login.username);
        contentValues.put(COL_LOGIN_PASSWORD, login.password);
        contentValues.put(COL_LOGIN_URL, login.url);
        db.update(TABLE_LOGIN_NAME, contentValues, COL_ID+"=?", new String[]{String.valueOf(login.id)});
        db.close();
    }

    public void deleteLogin(long login_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN_NAME, COL_ID+"=?", new String[]{String.valueOf(login_id)});
        db.close();
    }

    public Note[] allNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NOTE_NAME+" ORDER BY "+COL_NOTE_NAME,null);
        Note[] result = new Note[res.getCount()];
        int i = 0;
        while (res.moveToNext()) {
            result[i] = new Note(res.getLong(0), res.getString(1), res.getString(2));
            i++;
        }
        return result;
    }

    public Note[] notesByFolder(long folderID){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql;
        if (folderID < 1) {
            sql = "select * from "+TABLE_NOTE_NAME+" WHERE "+COL_NOTE_FOLDER_ID+" IS NULL ORDER BY "+COL_NOTE_NAME;
        } else {
            sql = "select * from "+TABLE_NOTE_NAME+" WHERE "+COL_NOTE_FOLDER_ID+" = "+folderID+" ORDER BY "+COL_NOTE_NAME;
        }
        Cursor res = db.rawQuery(sql,null);
        Note[] result = new Note[res.getCount()];
        int i = 0;
        while (res.moveToNext()) {
            result[i] = new Note(res.getLong(0), res.getString(1), res.getString(2));
            i++;
        }
        return result;
    }

    public long createNote(String name, String note){
        return createNote(name, note,-1);
    }

    public long createNote(String name, String note, long folder_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOTE_NAME, name);
        contentValues.put(COL_NOTE_TEXT, note);
        if (folder_id > 0) {
            contentValues.put(COL_NOTE_FOLDER_ID, folder_id);
        }
        long id = db.insert(TABLE_NOTE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOTE_NAME, note.name);
        contentValues.put(COL_NOTE_TEXT, note.note);
        db.update(TABLE_NOTE_NAME, contentValues, COL_ID+"=?", new String[]{String.valueOf(note.id)});
        db.close();
    }

    public void deleteNote(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE_NAME, COL_ID+"=?", new String[]{String.valueOf(note_id)});
        db.close();
    }


    private long folderCount(SQLiteDatabase db, long id) {
        String sql = "SELECT (SELECT COUNT(*) FROM "+TABLE_LOGIN_NAME+" WHERE "+COL_LOGIN_FOLDER_ID+" = "+id+") + (SELECT COUNT(*) FROM "+TABLE_NOTE_NAME+" WHERE "+COL_NOTE_FOLDER_ID+" = "+id+") as count";

        Cursor res = db.rawQuery(sql, null);
        if (!res.moveToNext()) return 0;
        return res.getLong(0);
    }

    public void generateTestData() {
        Map<String, Long> folder_id = new HashMap<>();
        String[] folder_names = {"Apple", "Crypto", "Desktop", "Github", "Gitlab", "Google", "Network", "Passport"};
        String[] url_links = {"apple.com", "devfolio.co", "google.com", "discord.com", "twitter.com", "linkedin.com", "amazon.in", "accounts.spotify.com"};
        String[] note_names = {"Router Config", "SSH Recovery", "Wallet", "Github", "Gitlab"};
        String[] usernames = {"jayakrishnan_jayu", "jayakrishnan", "jkay", "jay"};
        String[] passwords = {"password1", "password2", "password3", "12345678", "87654321"};
        String randomText = "Hopes and dreams were dashed that day. It should have been expected, but it still came as a shock. The warning signs had been ignored in favor of the possibility, however remote, that it could actually happen. That possibility had grown from hope to an undeniable belief it must be destiny. That was until it wasn't and the hopes and dreams came crashing down.";
        for (String name: folder_names) {
            long id = createFolder(name);
            folder_id.put(name, id);
        }

        Random random = new Random();

        for (String url: url_links){
            url = "https://"+url;
            String username = usernames[random.nextInt(usernames.length)];
            String password = passwords[random.nextInt(passwords.length)];
            long id = folder_id.get(folder_names[random.nextInt(folder_names.length)]);
            if (random.nextBoolean()) {
                Log.v("DB", "Folder ID "+ id);
                createLogin(username, password, url, id);
            } else {
                createLogin(username, password, url);
            }
        }

        for (String note: note_names) {
            long id = folder_id.get(folder_names[random.nextInt(folder_names.length)]);
            if (random.nextBoolean()) {
                Log.v("DB", "Folder ID "+ id);
                createNote(note, randomText, id);
            } else {
                createNote(note, randomText);
            }
        }

    }

    public void deleteAllData() {
        Note[] notes = allNotes();
        Login[] logins = allLogins();
        Folder[] folders = allFolders();
        for (Folder folder : folders) {
            deleteFolder(folder);
        }
        for (Login login : logins) {
            deleteLogin(login.id);
        }
        for (Note note : notes) {
            deleteNote(note.id);
        }
    }
}
