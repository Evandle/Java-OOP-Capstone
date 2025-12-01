package capstone;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {

    private final String[] COLUMN_NAMES = {"ID", "Name", "Price", "Stock", "Category ID"};
    private final List<Item> itemList;

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

        return switch (columnIndex) {
            case 0 -> item.getId();
            case 1 -> item.getItemName();
            case 2 -> item.getPrice();
            case 3 -> item.getStock();
            case 4 -> item.getCategoryId();
            default -> "N/A";
        };
    }
}

