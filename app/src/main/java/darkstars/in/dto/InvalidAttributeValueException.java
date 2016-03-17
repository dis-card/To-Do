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

package darkstars.in.dto;

/**
 * Created by Vikash Kumar on 3/10/2016.
 * Thrown if tries to set invalid value for an attribute.
 */
public class InvalidAttributeValueException extends Exception {

    /**
     * @param msg, error message
     */
    public InvalidAttributeValueException(String msg) {
        super(msg);
    }

    /**
     * @param e, root exception while chaining.
     */
    public InvalidAttributeValueException(Exception e) {
        super(e);
    }

    /**
     * @param msg, error message.
     * @param e, root exception while chaining.
     */
    public InvalidAttributeValueException(String msg, Exception e) {
        super(msg, e);
    }
}
