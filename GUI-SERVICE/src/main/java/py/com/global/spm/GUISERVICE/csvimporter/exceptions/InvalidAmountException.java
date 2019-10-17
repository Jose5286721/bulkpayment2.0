package py.com.global.spm.GUISERVICE.csvimporter.exceptions;

public class InvalidAmountException extends Exception {

    private final int row;

    public InvalidAmountException(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }
}

