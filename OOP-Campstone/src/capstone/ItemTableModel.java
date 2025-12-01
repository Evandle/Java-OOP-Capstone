package capstone;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {

    private final String[] COLUMN_NAMES = {"ID", "Name", "Price", "Stock", "Category ID"};
    private List<Item> itemList;

    public ItemTableModel(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getRowCount() {
        return itemList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = itemList.get(rowIndex);

        switch (columnIndex) {
            case 0: return item.getId();
            case 1: return item.getItemName();
            case 2: return item.getPrice();
            case 3: return item.getStock();
            case 4: return item.getCategoryId();
            default: return "N/A";
        }
    }
}