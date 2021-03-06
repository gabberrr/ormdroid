/*
 * Copyright 2012 Ross Bamford
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.roscopeco.ormdroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

/*
 * Map java.util.Date to the database.
 * 
 * This implementation just stashes the number of seconds
 * since the epoch in a BIGINT.
 */
public class DateTypeMapping implements TypeMapping {
  private Class<?> mJavaType; 
  private String mSqlType;
  
  public DateTypeMapping() {
    mJavaType = Date.class;
    mSqlType = "BIGINT";
  }

  public Class<?> javaType() {
    return mJavaType;
  }

  public String sqlType(Class<?> concreteType) {
    return mSqlType;
  }

  public String encodeValue(SQLiteDatabase db, Object value) {
    return "\"" + ((Date)value).getTime() + "\"";
  }

  public <T extends Entity> Object decodeValue(SQLiteDatabase db, Field field, Cursor c, int columnIndex, ArrayList<T> precursors) {
    return new Date(c.getLong(columnIndex));
  }
}