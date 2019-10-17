package py.com.global.spm.model.systemparameters;

import java.util.Arrays;
import java.util.Optional;

public enum DriversParameters {

    NOTIFICATIONMANAGER_DRIVERCLASSPATH("notificationManager.driverClasspath", null),
    MONETARYTRANSACTION_DRIVERCLASSPATH("monetaryTransaction.driverClasspath", null);

    private String dataBase;
    private String defaultt;

    DriversParameters(String dataBase, String defaultt) {
        this.dataBase = dataBase;
        this.defaultt = defaultt;
    }
    public static Optional<DriversParameters> valueOfDatabase(String dataBase) {
        return Arrays.stream(values())
                .filter(sP -> sP.dataBase.equals(dataBase))
                .findFirst();
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getDefault() {
        return defaultt;
    }

    public void setDefaultt(String defaultt) {
        this.defaultt = defaultt;
    }
}
