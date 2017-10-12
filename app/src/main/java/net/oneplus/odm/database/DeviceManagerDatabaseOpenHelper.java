package net.oneplus.odm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DeviceManagerDatabaseOpenHelper extends SQLiteOpenHelper {
  private static String CREATE_DEVICEINFO_PAYLOAD_UPLOAD_TABLE;
  private static String CREATE_PAYLOAD_UPLOAD_TABLE;

  static {
    CREATE_PAYLOAD_UPLOAD_TABLE =
        "CREATE TABLE OP_payload_upload_table (OP_payload_id INTEGER PRIMARY KEY AUTOINCREMENT,OP_payload TEXT,OP_payload_category TEXT,OP_payload_timestamp TEXT)";
    CREATE_DEVICEINFO_PAYLOAD_UPLOAD_TABLE =
        "CREATE TABLE OP_deviceinfo_payload_upload_table (OP_deviceinfo_payload_id INTEGER PRIMARY KEY AUTOINCREMENT,OP_deviceinfo_payload TEXT,OP_deviceinfo_payload_timestamp TEXT)";
  }

  public DeviceManagerDatabaseOpenHelper(Context context, String name, CursorFactory factory,
      int version) {
    super(context, name, null, version);
  }

  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_PAYLOAD_UPLOAD_TABLE);
    db.execSQL(CREATE_DEVICEINFO_PAYLOAD_UPLOAD_TABLE);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
