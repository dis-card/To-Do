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

/**
 * Created by kukuvika on 3/6/2016.
 */
public class StringUtil {

    public static boolean isNull(String str) {
        return str == null;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        boolean empty = false;
        if (str != null) {
           str = str.trim();
            if (str.equals(""))
                empty = true;
        }
        return empty;
    }
}
