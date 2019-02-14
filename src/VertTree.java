public class VertTree {
    String id;
    int type;
    VertTree left;
    VertTree right;
    VertTree parent;
    TableData tableData;

    public VertTree(int type, String id, VertTree parent, TableData tableData) {
        this.parent = parent;
        this.id = id;
        this.type = type;
        this.tableData = tableData;
    }

    public VertTree(int type, String id) {
        this.parent = null;
        this.id = id;
        this.type = type;
        tableData = new TableData(id, type, new SemType(type));
    }


}
