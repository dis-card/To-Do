/*
 *      Copyright (C) 2016  Vikash Kumar
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package darkstars.in.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;

import darkstars.in.dto.Task;
import darkstars.in.util.ListUtil;

/**
 * Created by kukuvika on 3/6/2016.
 */
public class ToDoDatabaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "TODO";
    public static final String TABLE_NAME = DB_NAME;
    public static final int DB_VERSION = 1;
    public static final String TASK_COL = "TASK";
    public static final String COMMENT_COL = "COMMENT";
    public static final String DATE_COL = "DATE";
    public static final String ID_COL = "_ID";
    public static final String TIME_COL = "TIME";
    public static final String [] allColumns = {ID_COL, DATE_COL, TASK_COL, TIME_COL, COMMENT_COL};
    private static final String CREATE_DB = "CREATE TABLE "+ TABLE_NAME
                                                         + " ("
                                                         + " _ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                         + DATE_COL +" INTEGER NOT NULL, "
                                                         + TASK_COL +" VARCHAR2(3000) NOT NULL, "
                                                         + TIME_COL +" INTEGER, "
                                                         + COMMENT_COL +" VARCHAR2(5000)"
                                                         + " )";

    private static final String selectOnDate = " SELECT "+ID_COL + " "
                                                        +DATE_COL + " "
                                                        +TASK_COL + " "
                                                        +TIME_COL + " "
                                                        +COMMENT_COL + " FROM "
                                                        +DB_NAME + " WHERE "
                                                        +DATE_COL + " = ?";

    private Context ctx;

    public ToDoDatabaseHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
        this.ctx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Task> getTask(Date date) {
        List<Task> taskList = ListUtil.newList();
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, allColumns, DATE_COL+" = ?",new String[] {String.valueOf(date.getTime())}, null, null, null);
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getLong(0));
            task.setTaskDate(new Date(cursor.getLong(1)));
            task.setTaskName(cursor.getString(2));
            task.setReminderTime(cursor.getLong(3));
            task.setComment(cursor.getString(4));
            taskList.add(task);
        }
        return taskList;
    }

    public int delete(List<Task> tasks) {
        String[] idArray = new String[tasks.size()];
        int recDeleted = 0;
        for (int i = 0; i < tasks.size(); i++) {
            idArray[i] = String.valueOf(tasks.get(i).getId());
        }
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransactionNonExclusive();
        recDeleted = db.delete(DB_NAME,  ID_COL+" IN (" + new String(new char[idArray.length - 1]).replace("\0", "?,") + "?)", idArray );
        if (recDeleted == tasks.size()) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();
        db.close();
        return recDeleted;
    }

    public void close() {
        super.close();
    }
}
