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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import darkstars.in.dto.Task;
import darkstars.in.util.DateUtil;
import darkstars.in.util.ListUtil;

/**
 * Created by Vikash Kumar on 3/12/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Task> taskList;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<Long> selectedTaskIds = ListUtil.newList();

    public ExpandableListAdapter(Context ctx, List<Task> tasks) {
        super();
        this.ctx = ctx;
        layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.taskList = tasks;
    }

    @Override
    public int getGroupCount() {
        return taskList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return taskList.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return taskList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return taskList.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return taskList.get(groupPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_header, null);
        }
        if (!ListUtil.isEmpty(taskList)) {
            TextView taskName = (TextView) convertView.findViewById(R.id.taskNameLabel);
            taskName.setText(taskList.get(groupPosition).getTaskName());
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        if (!ListUtil.isEmpty(taskList)) {
            //TextView taskName = (TextView) convertView.findViewById(R.id.taskName);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView reminder = (TextView) convertView.findViewById(R.id.reminder);
            TextView comment = (TextView) convertView.findViewById(R.id.comment);
            Task task = taskList.get(groupPosition);
            date.setText(DateUtil.formatDate(task.getTaskDate()));
            long reminderTime = task.getReminderTime();
            String reminderTimeText = null;
            if (reminderTime == 0) {
                reminderTimeText = "N/A";
            } else {
                Date dateTime = new Date(reminderTime);
                reminderTimeText = DateUtil.getTimeString(dateTime.getHours(), dateTime.getMinutes());
            }
            reminder.setText(reminderTimeText);
            comment.setText(String.valueOf(task.getComment()));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * Get multiple groups according to their position.
     * @param positions, list of group positions.
     * @return, list of group objects.
     */
    public List getGroups(List<Integer> positions) {
        List<Object> groups = ListUtil.newList();
        for (int pos : positions) {
            groups.add(((Task)getGroup(pos)));
        }
        return groups;
    }

    /**
     * Delete items from the adpater and fires data set changed notification.
     * @param items
     */
    public void removeItems(List<Task> items) {
        for (Task task : items) {
            taskList.remove(task);
        }
        notifyDataSetChanged();
    }
}
