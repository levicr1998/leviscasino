package org.cri.levi.websocketcasinoserver.database;

public class SqlModel {
    private Object item;
    private int position;

    public Object getItem() {
        return item;
    }

    public int getPosition() {
        return position;
    }

    public SqlModel(int position, Object item) {
        this.position = position;
        this.item = item;
    }
}
