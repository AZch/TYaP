public class SemType {
    int type = 0;
    int typeData = 0;

    public SemType(int type) {
        this.typeData = type;
    }

    public SemType(int type, int typeData) {
        this.type = type;
        this.typeData = typeData;
    }

    public SemType(SemType target) {
        if (target != null) {
            this.typeData = target.typeData;
            this.type = target.type;
        }
    }
}
