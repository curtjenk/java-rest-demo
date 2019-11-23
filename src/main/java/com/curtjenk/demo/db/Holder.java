package com.curtjenk.demo.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Holder {
		private final Field field;
		private final DbColumn column;
		private Method rsMethod;
		private int place;

		public Holder(Field field, DbColumn column) {
			this.field = field;
			this.column = column;
		}

		public Method getRsMethod() {
			return rsMethod;
		}

		public void setRsMethod(Method rsMethod) {
			this.rsMethod = rsMethod;
		}

		public Field getField() {
			return field;
		}

		public DbColumn getColumn() {
			return column;
		}

		public int getPlace() {
			return place;
		}

		public void setPlace(int place) {
			this.place = place;
		}
		
	}