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

package darkstars.in.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import darkstars.in.service.ToDoService;
import darkstars.in.util.DateUtil;
import darkstars.in.util.MsgUtil;
import darkstars.in.util.StringUtil;

public class Home extends AppCompatActivity {

    //public static final String DATE_SEPARATOR = "/";
    public static final String TIME_SEPARATOR = ":";
    public static final String APP_NAME = "To do";

    // UI Components.
    private EditText taskText;
    private TextView dateText;
    private TextView timeText;
    private TextView timeLabel;
    private CheckBox reminderCheckBox;
    private EditText commentText;
    private Button addButton;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    // Listeners.
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private int date, month, year, hour, min;
    private Date currentDate;
    private final StringBuilder dateString = new StringBuilder();
    private final StringBuilder timeString = new StringBuilder();

    private ToDoService toDoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toDoService = new ToDoService(this);
        initView();
        refreshCurrentDate();
        setDate(DateUtil.getCurrentDateString());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void onDateClick(View view) {
        refreshCurrentDate();
        datePicker = new DatePickerDialog(Home.this, getDateListener(), year, month, date);
        datePicker.show();
    }

    public void onTimeClick(View view) {
        refreshCurrentTime();
        timePicker = new TimePickerDialog(Home.this, getTimeListener(), hour, min, true);
        timePicker.show();
    }

    /**
     * Add button OnClick listener.
     * @param view, add button
     */
    public void onAddClick(View view) {
        if (StringUtil.isEmpty(String.valueOf(taskText.getText().toString()))) {
            MsgUtil.showMsgLong(this, R.string.taskEmptyError);
        } else {
            long id = toDoService.addTask(taskText.getText().toString(), getDate(), getReminderTime(), String.valueOf(commentText.getText().toString()));
            MsgUtil.showMsgShort(this, R.string.taskAddSuc);
            Log.i(APP_NAME, "Task added, Id is " + String.valueOf(id));
            resetAll();
        }
    }

    /**
     * Reminder checkbox OnClick listener.
     * @param view, reminder check box.
     */
    public void onReminderCheck(View view) {
        CheckBox reminderCheckBox = (CheckBox) view;
        if (reminderCheckBox.isChecked()) {
            refreshCurrentTime();
            setTime(DateUtil.getTimeString(hour, min));
            showTimeComponents();
        } else {
            hideTimeComponents();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_task_list:
                Intent taskList = new Intent(Home.this, TaskList.class);
                startActivity(taskList);
                break;
            case R.id.action_preferences:
                break;
            case R.id.addShortCut:
                onAddClick(item.getActionView());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toDoService.close();
    }

    /**
     * Gets current date and set them to instance variables.
     */
    private void refreshCurrentDate() {
        Calendar cal = Calendar.getInstance();
        date = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
    }

    /**
     * Gets current time and set them to instance variables.
     * We ignore seconds and milliseconds.
     */
    private void refreshCurrentTime() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
    }

    /**
     * Gets called when the date is set using date picker.
     * @return, date set listener.
     */
    private DatePickerDialog.OnDateSetListener getDateListener() {
        if (dateSetListener == null) {
            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int currentYear, int monthOfYear, int dayOfMonth) {
                    date = dayOfMonth;
                    year = currentYear;
                    month = monthOfYear;
                    setDate(DateUtil.getDateString(date, month, year));
                }
            };
        }
        return dateSetListener;
    }

    /**
     * Gets called when time set using time picker.
     * @return, time set listener.
     */
    private TimePickerDialog.OnTimeSetListener getTimeListener() {
        if (timeSetListener == null) {
            timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour = hourOfDay;
                    min = minute;
                    setTime(DateUtil.getTimeString(hour, min));
                }
            };
        }
        return timeSetListener;
    }

    /**
     * Sets date in date text component.
     * @param date, string representation of the date.
     */
    private void setDate(String date) {
        dateText.setText(date);
    }

    /**
     * Sets time in time text component.
     * @param time, string representation of the time.
     */
    private void setTime(String time) {
        timeText.setText(time);
    }

    /**
     * Empties content of task text component.
     */
    private void resetTaskText() {
        taskText.setText("");
    }

    /**
     * Empties content of comment text component.
     */
    private void resetCommentText() {
        commentText.setText("");
    }

    private void resetReminderCheckBox() {
        reminderCheckBox.setChecked(false);
    }

    /**
     * @return
     */
    private Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @return
     */
    private long getReminderTime() {
        long time = 0;
        if (reminderCheckBox.isChecked()) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, date, hour, min, 0);
            cal.set(Calendar.MILLISECOND, 0);
            time = cal.getTimeInMillis();
        }
        return time;
    }

    /**
     * Hide time label and time text.
     */
    private void hideTimeComponents() {
        timeLabel.setVisibility(View.INVISIBLE);
        timeText.setVisibility(View.INVISIBLE);
    }

    /**
     * Show time label and time text.
     */
    private void showTimeComponents() {
        timeLabel.setVisibility(View.VISIBLE);
        timeText.setVisibility(View.VISIBLE);
    }

    /**
     * Resets all the UI components.
     */
    private void resetAll() {
        resetTaskText();
        resetReminderCheckBox();
        hideTimeComponents();
        resetCommentText();
    }

    /**
     * Initialize all view components.
     * If new component is added, should be initialized here.
     */
    private void initView() {
        taskText = (EditText) findViewById(R.id.taskText);
        dateText = (TextView) findViewById(R.id.dateText);
        reminderCheckBox = (CheckBox) findViewById(R.id.reminderCheckBox);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        timeText = (TextView) findViewById(R.id.timeText);
        commentText = (EditText) findViewById(R.id.commentText);
        addButton = (Button) findViewById(R.id.addButton);
    }
}
