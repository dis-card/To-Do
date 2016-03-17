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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vikash Kumar on 3/10/2016.
 */
public class ListUtil {

    /**
     * Returns an ArrayList with type as T.
     * @param <T>, any type.
     * @return, an ArrayList with type as T.
     */
    public static <T> List<T> newList() {
        return new ArrayList<T>();
    }

    /**
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        boolean empty = false;
        if (list == null || list.size() == 0) {
            empty = true;
        }
        return empty;
    }
}
