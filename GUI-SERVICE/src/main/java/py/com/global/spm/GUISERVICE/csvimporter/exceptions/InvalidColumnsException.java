package py.com.global.spm.GUISERVICE.csvimporter.exceptions;

public class InvalidColumnsException extends Exception {

    private final int row;

    public InvalidColumnsException(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }
}

