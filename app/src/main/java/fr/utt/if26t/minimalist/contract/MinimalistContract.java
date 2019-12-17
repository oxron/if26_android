package fr.utt.if26t.minimalist.contract;

import android.provider.BaseColumns;

public class MinimalistContract {

    public MinimalistContract() {}
    //separate innerclasss for each table

    public static final class ListEntry implements BaseColumns {
        public static final String TABLE_NAME = "table_list";
        public static final String COLUMN_NAME = "name";
    }
}
