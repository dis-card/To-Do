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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import darkstars.in.dto.Task;
import darkstars.in.service.ToDoService;
import darkstars.in.util.DateUtil;
import darkstars.in.util.ListUtil;
import darkstars.in.util.MsgUtil;

public class TaskList extends AppCompatActivity {

    private TextView dateText;
    private ExpandableListView taskListView;
    private int day, month, year;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog datePicker;
    private ToDoService toDoService;
    private ExpandableListAdapter expandedListAdapter;
    private boolean actionModeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // All UI component references should be set with correspoding objects here, it is must.
        // So, that later they can be referred easily.
        dateText = (TextView) findViewById(R.id.dateText);
        taskListView = (ExpandableListView) findViewById(R.id.taskListView);
        taskListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        taskListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (actionModeEnabled) {
                    boolean selected = parent.isItemChecked(groupPosition);
                    parent.setItemChecked(groupPosition, !selected);
                }
                return actionModeEnabled;
            }
        });
        taskListView.setMultiChoiceModeListener(new MultiChoiceModeListener());

        refreshCurrentDate();
        setDate(DateUtil.getDateString(day, month, year));

        toDoService = new ToDoService(this);
        setTaskList(getTasks(getDate()));

    }

    public void onDateClick(View view) {
        refreshCurrentDate();
        datePicker = new DatePickerDialog(TaskList.this, getDateSetListener(), year, month, day);
        datePicker.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toDoService.close();
    }

    private DatePickerDialog.OnDateSetListener getDateSetListener() {
        if (dateSetListener == null) {
            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    TaskList.this.day = dayOfMonth;
                    TaskList.this.month = monthOfYear;
                    TaskList.this.year = year;
                    setDate(DateUtil.getDateString(day, month, year));
                    setTaskList(getTasks(getDate()));
                }
            };
        }
        return dateSetListener;
    }

    private void refreshCurrentDate() {
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
    }

    private void setDate(String date) {
        dateText.setText(date);
    }

    private String getSelectedDate() {
        return String.valueOf(dateText.getText().toString());
    }

    private Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private List<Task> getTasks(Date date) {
        return toDoService.getTask(date);
    }

    private void setTaskList(List<Task> taskList) {
        expandedListAdapter = new ExpandableListAdapter(this, taskList);
        taskListView.setAdapter(expandedListAdapter);
    }

    private class MultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.task_context_menu, menu);
            actionModeEnabled = true;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.done:
                case R.id.delete:
                    SparseBooleanArray selectItems = taskListView.getCheckedItemPositions();
                    List<Integer> selectedItemPos = getItemPos(selectItems);
                    List groups = expandedListAdapter.getGroups(selectedItemPos);
                    toDoService.deleteTasks(groups);
                    expandedListAdapter.removeItems(groups);
                    MsgUtil.showMsgLong(TaskList.this, R.string.taskDelSuc);
                    mode.finish();
                    break;
                case R.id.postPone:
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mode.invalidate();
            actionModeEnabled = false;
        }

        /**
         * Gets list of selected item positions.
         * @param selectedArray, sparse array of selected items.
         * @return, positions of selected items.
         */
        private List<Integer> getItemPos(SparseBooleanArray selectedArray) {
            List<Integer> selectedPosition = ListUtil.newList();
            for (int i = 0; i < selectedArray.size(); i++) {
                int position = selectedArray.keyAt(i);
                if (selectedArray.valueAt(i))
                    selectedPosition.add(i);
            }
            return selectedPosition;
        }


    }
}
