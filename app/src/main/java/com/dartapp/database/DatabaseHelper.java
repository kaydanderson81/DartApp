package com.dartapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dartapp.model.Player;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "players.db";
    public static final String TABLE_NAME = "players_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "FAV_DOUBLES";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, FAV_DOUBLES TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, player.getName());
        contentValues.put(COL3, player.getFavouriteDoubles());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("Range")
    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Player player = new Player();
                    player.setId(cursor.getLong(cursor.getColumnIndex(COL1)));
                    player.setName(cursor.getString(cursor.getColumnIndex(COL2)));

                    String doublesString = cursor.getString(cursor.getColumnIndex(COL3));
                    player.setFavouriteDoubles(doublesString);

                    playerList.add(player);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return playerList;
    }


    @SuppressLint("Range")
    public Player getPlayerByName(String playerName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL1, COL2, COL3};
        String selection = COL2 + " = ?";
        String[] selectionArgs = {playerName};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        Player player = null;

        if (cursor != null && cursor.moveToFirst()) {
            long playerId = cursor.getLong(cursor.getColumnIndex(COL1));
            String playerFavDoubles = cursor.getString(cursor.getColumnIndex(COL3));

            // Create a Player object with the retrieved data
            player = new Player();
            player.setId(playerId);
            player.setName(playerName);
            player.setFavouriteDoubles(playerFavDoubles);

            // Close the cursor
            cursor.close();
        }

        // Close the database
        db.close();

        return player;
    }

    public boolean updateData(Long id, String name, String favDoubles) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, favDoubles);

        int rowsAffected = db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {String.valueOf(id)});
        Log.d("DatabaseHelper", "Rows affected during update: " + rowsAffected);
        return rowsAffected > 0;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
