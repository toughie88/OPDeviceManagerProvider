package net.oneplus.odm.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import net.oneplus.odm.database.DeviceManagerDatabaseOpenHelper;

public class PayloadProvider extends ContentProvider {
  private static final String AUTHORITY;
  private static final UriMatcher sURIMatcher;

  static {
    AUTHORITY = PayloadProvider.class;
    sURIMatcher = new UriMatcher(-1);
    UriMatcher uriMatcher = sURIMatcher;
    String str = AUTHORITY;
    String str2 = "OP_payload_upload_table";
    uriMatcher = sURIMatcher;
    str = AUTHORITY;
    str2 = "OP_payload_upload_table/#";
    uriMatcher = sURIMatcher;
    str = AUTHORITY;
    str2 = "OP_deviceinfo_payload_upload_table";
    uriMatcher = sURIMatcher;
    str = AUTHORITY;
    str2 = "OP_deviceinfo_payload_upload_table/#";
  }

  private DeviceManagerDatabaseOpenHelper mDatabaseHelper;

  public boolean onCreate() {
    this.mDatabaseHelper =
        new DeviceManagerDatabaseOpenHelper(getContext(), "DeviceManager", null, 1);
    return false;
  }

  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
    switch (sURIMatcher.match(uri)) {
      case 1:
        queryBuilder.setTables("OP_payload_upload_table");
        break;
      case 2:
        queryBuilder.setTables("OP_payload_upload_table");
        queryBuilder.appendWhere("OP_payload_id=" + uri.getLastPathSegment());
        break;
      case 3:
        queryBuilder.setTables("OP_deviceinfo_payload_upload_table");
        break;
      case 4:
        queryBuilder.setTables("OP_deviceinfo_payload_upload_table");
        queryBuilder.appendWhere("OP_deviceinfo_payload_id=" + uri.getLastPathSegment());
        break;
      default:
        throw new IllegalArgumentException("Unknown URI");
    }
    Cursor cursor =
        queryBuilder.query(this.mDatabaseHelper.getReadableDatabase(), projection, selection,
            selectionArgs, null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  public String getType(Uri uri) {
    return null;
  }

  public Uri insert(Uri uri, ContentValues values) {
    long id;
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = this.mDatabaseHelper.getWritableDatabase();
    String tableUri = "";
    switch (uriType) {
      case 1:
        tableUri = "OP_payload_upload_table";
        id = sqlDB.insert("OP_payload_upload_table", null, values);
        break;
      case 3:
        tableUri = "OP_deviceinfo_payload_upload_table";
        id = sqlDB.insert("OP_deviceinfo_payload_upload_table", null, values);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return Uri.parse(tableUri + "/" + id);
  }

  public int delete(Uri uri, String selection, String[] selectionArgs) {
    int rowsDeleted;
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = this.mDatabaseHelper.getWritableDatabase();
    String id = "";
    switch (uriType) {
      case 1:
        rowsDeleted = sqlDB.delete("OP_payload_upload_table", selection, selectionArgs);
        break;
      case 2:
        id = uri.getLastPathSegment();
        if (!TextUtils.isEmpty(selection)) {
          rowsDeleted =
              sqlDB.delete("OP_payload_upload_table", "OP_payload_id=" + id + " and " + selection,
                  selectionArgs);
          break;
        }
        rowsDeleted = sqlDB.delete("OP_payload_upload_table", "OP_payload_id=" + id, null);
        break;
      case 3:
        rowsDeleted = sqlDB.delete("OP_deviceinfo_payload_upload_table", selection, selectionArgs);
        break;
      case 4:
        id = uri.getLastPathSegment();
        if (!TextUtils.isEmpty(selection)) {
          rowsDeleted = sqlDB.delete("OP_payload_upload_table",
              "OP_deviceinfo_payload_id=" + id + " and " + selection, selectionArgs);
          break;
        }
        rowsDeleted =
            sqlDB.delete("OP_deviceinfo_payload_upload_table", "OP_deviceinfo_payload_id=" + id,
                null);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsDeleted;
  }

  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
