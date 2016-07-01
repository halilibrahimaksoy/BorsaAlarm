package io.realm;


import com.halilibrahimaksoy.borsaalarm.model.*;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.*;

public class FollowExchangeRealmProxy extends FollowExchange {

    @Override
    public String getName() {
        return (java.lang.String) realmGetRow().getString(Realm.columnIndices.get("FollowExchange").get("name"));
    }

    @Override
    public void setName(String value) {
        realmGetRow().setString(Realm.columnIndices.get("FollowExchange").get("name"), (String) value);
    }

    @Override
    public java.util.Date getCreateDate() {
        return (java.util.Date) realmGetRow().getDate(Realm.columnIndices.get("FollowExchange").get("createDate"));
    }

    @Override
    public void setCreateDate(java.util.Date value) {
        realmGetRow().setDate(Realm.columnIndices.get("FollowExchange").get("createDate"), (Date) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if(!transaction.hasTable("class_FollowExchange")) {
            Table table = transaction.getTable("class_FollowExchange");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.DATE, "createDate");
            return table;
        }
        return transaction.getTable("class_FollowExchange");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if(transaction.hasTable("class_FollowExchange")) {
            Table table = transaction.getTable("class_FollowExchange");
            if(table.getColumnCount() != 2) {
                throw new IllegalStateException("Column count does not match");
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for(long i = 0; i < 2; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }
            if (!columnTypes.containsKey("name")) {
                throw new IllegalStateException("Missing column 'name'");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new IllegalStateException("Invalid type 'String' for column 'name'");
            }
            if (!columnTypes.containsKey("createDate")) {
                throw new IllegalStateException("Missing column 'createDate'");
            }
            if (columnTypes.get("createDate") != ColumnType.DATE) {
                throw new IllegalStateException("Invalid type 'Date' for column 'createDate'");
            }
        }
    }

    public static List<String> getFieldNames() {
        return Arrays.asList("name", "createDate");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("FollowExchange = [");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("} ");
        stringBuilder.append("{createDate:");
        stringBuilder.append(getCreateDate());
        stringBuilder.append("} ");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int result = 17;
        String aString_0 = getName();
        result = 31 * result + (aString_0 != null ? aString_0.hashCode() : 0);
        java.util.Date temp_1 = getCreateDate();
        result = 31 * result + (temp_1 != null ? temp_1.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowExchangeRealmProxy aFollowExchange = (FollowExchangeRealmProxy)o;
        if (getName() != null ? !getName().equals(aFollowExchange.getName()) : aFollowExchange.getName() != null) return false;
        if (getCreateDate() != null ? !getCreateDate().equals(aFollowExchange.getCreateDate()) : aFollowExchange.getCreateDate() != null) return false;
        return true;
    }

}
