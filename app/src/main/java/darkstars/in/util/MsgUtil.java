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

package darkstars.in.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Vikash Kumar on 3/12/2016.
 */
public class MsgUtil {

    /**
     * Shows message for long period.
     * @param ctx, context
     * @param msgId, message resource id.
     */
    public static void showMsgLong(Context ctx, int msgId) {
        Toast.makeText(ctx, ResourcesUtil.getString(ctx, msgId), Toast.LENGTH_LONG).show();
    }

    /**
     * Shows message for small period.
     * @param ctx, context
     * @param msgId, message resource id.
     */
    public static void showMsgShort(Context ctx, int msgId) {
        Toast.makeText(ctx, ResourcesUtil.getString(ctx, msgId), Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows message for long period.
     * @param ctx, context
     * @param msg, message string.
     */
    public static void showMsgLong(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
