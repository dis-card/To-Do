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

package darkstars.in.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.List;

import darkstars.in.db.ToDoDatabaseHelper;
import darkstars.in.dto.Task;
import darkstars.in.todo.Home;

/**
 * Created by Vikash Kumar on 3/10/2016.
 */
public class ToDoService {

    private ToDoDatabaseHelper dbHelper;
    private Context ctx;

    public ToDoService(Context ctx) {
        this.ctx = ctx;
        dbHelper = new ToDoDatabaseHelper(ctx);
    }

    /**
     * Adds a task.
     * @param taskName, name of the task.
     * @param date, date on which task should be performed.
     * @param time, at which reminder is required.
     * @param comment, any comment about the task.
     * @return task identifier.
     */
    public long addTask(String taskName, Date date, long time, String comment) {
        ContentValues values = new ContentValues();
        values.put(ToDoDatabaseHelper.TASK_COL, taskName);
        values.put(ToDoDatabaseHelper.COMMENT_COL, comment);
        values.put(ToDoDatabaseHelper.DATE_COL, date.getTime());
        values.put(ToDoDatabaseHelper.TIME_COL, time);
        return dbHelper.insert(values);
    }

    public List<Task> getTask(Date date) {
        List<Task> taskList = dbHelper.getTask(date);
        Log.i(Home.APP_NAME, taskList.toString());
        return taskList;
    }

    public void editTask(Task task) {

    }

    /**
     * Delete tasks.
     * @param tasks, tasks.
     * @return, number of task deleted.
     */
    public long deleteTasks(List<Task> tasks) {
        return dbHelper.delete(tasks);
    }

    public void close() {
        dbHelper.close();
    }
}
