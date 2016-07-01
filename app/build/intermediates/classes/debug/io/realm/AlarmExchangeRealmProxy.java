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

public class AlarmExchangeRealmProxy extends AlarmExchange {

    @Override
    public String getName() {
        return (java.lang.String) realmGetRow().getString(Realm.columnIndices.get("AlarmExchange").get("name"));
    }

    @Override
    public void setName(String value) {
        realmGetRow().setString(Realm.columnIndices.get("AlarmExchange").get("name"), (String) value);
    }

    @Override
    public java.util.Date getCreateDate() {
        return (java.util.Date) realmGetRow().getDate(Realm.columnIndices.get("AlarmExchange").get("createDate"));
    }

    @Override
    public void setCreateDate(java.util.Date value) {
        realmGetRow().setDate(Realm.columnIndices.get("AlarmExchange").get("createDate"), (Date) value);
    }

    @Override
    public float getPrice() {
        return (float) realmGetRow().getFloat(Realm.columnIndices.get("AlarmExchange").get("price"));
    }

    @Override
    public void setPrice(float value) {
        realmGetRow().setFloat(Realm.columnIndices.get("AlarmExchange").get("price"), (float) value);
    }

    @Override
    public float getMinPrice() {
        return (float) realmGetRow().getFloat(Realm.columnIndices.get("AlarmExchange").get("minPrice"));
    }

    @Override
    public void setMinPrice(float value) {
        realmGetRow().setFloat(Realm.columnIndices.get("AlarmExchange").get("minPrice"), (float) value);
    }

    @Override
    public float getMaxPrice() {
        return (float) realmGetRow().getFloat(Realm.columnIndices.get("AlarmExchange").get("maxPrice"));
    }

    @Override
    public void setMaxPrice(float value) {
        realmGetRow().setFloat(Realm.columnIndices.get("AlarmExchange").get("maxPrice"), (float) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if(!transaction.hasTable("class_AlarmExchange")) {
            Table table = transaction.getTable("class_AlarmExchange");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.DATE, "createDate");
            table.addColumn(ColumnType.FLOAT, "price");
            table.addColumn(ColumnType.FLOAT, "minPrice");
            table.addColumn(ColumnType.FLOAT, "maxPrice");
            return table;
        }
        return transaction.getTable("class_AlarmExchange");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if(transaction.hasTable("class_AlarmExchange")) {
            Table table = transaction.getTable("class_AlarmExchange");
            if(table.getColumnCount() != 5) {
                throw new IllegalStateException("Column count does not match");
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for(long i = 0; i < 5; i++) {
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
            if (!columnTypes.containsKey("price")) {
                throw new IllegalStateException("Missing column 'price'");
            }
            if (columnTypes.get("price") != ColumnType.FLOAT) {
                throw new IllegalStateException("Invalid type 'float' for column 'price'");
            }
            if (!columnTypes.containsKey("minPrice")) {
                throw new IllegalStateException("Missing column 'minPrice'");
            }
            if (columnTypes.get("minPrice") != ColumnType.FLOAT) {
                throw new IllegalStateException("Invalid type 'float' for column 'minPrice'");
            }
            if (!columnTypes.containsKey("maxPrice")) {
                throw new IllegalStateException("Missing column 'maxPrice'");
            }
            if (columnTypes.get("maxPrice") != ColumnType.FLOAT) {
                throw new IllegalStateException("Invalid type 'float' for column 'maxPrice'");
            }
        }
    }

    public static List<String> getFieldNames() {
        return Arrays.asList("name", "createDate", "price", "minPrice", "maxPrice");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("AlarmExchange = [");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("} ");
        stringBuilder.append("{createDate:");
        stringBuilder.append(getCreateDate());
        stringBuilder.append("} ");
        stringBuilder.append("{price:");
        stringBuilder.append(getPrice());
        stringBuilder.append("} ");
        stringBuilder.append("{minPrice:");
        stringBuilder.append(getMinPrice());
        stringBuilder.append("} ");
        stringBuilder.append("{maxPrice:");
        stringBuilder.append(getMaxPrice());
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
        float aFloat_2 = getPrice();
        result = 31 * result + (aFloat_2 != +0.0f ? Float.floatToIntBits(aFloat_2) : 0);
        float aFloat_3 = getMinPrice();
        result = 31 * result + (aFloat_3 != +0.0f ? Float.floatToIntBits(aFloat_3) : 0);
        float aFloat_4 = getMaxPrice();
        result = 31 * result + (aFloat_4 != +0.0f ? Float.floatToIntBits(aFloat_4) : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmExchangeRealmProxy aAlarmExchange = (AlarmExchangeRealmProxy)o;
        if (getName() != null ? !getName().equals(aAlarmExchange.getName()) : aAlarmExchange.getName() != null) return false;
        if (getCreateDate() != null ? !getCreateDate().equals(aAlarmExchange.getCreateDate()) : aAlarmExchange.getCreateDate() != null) return false;
        if (Float.compare(getPrice(), aAlarmExchange.getPrice()) != 0) return false;
        if (Float.compare(getMinPrice(), aAlarmExchange.getMinPrice()) != 0) return false;
        if (Float.compare(getMaxPrice(), aAlarmExchange.getMaxPrice()) != 0) return false;
        return true;
    }

}
