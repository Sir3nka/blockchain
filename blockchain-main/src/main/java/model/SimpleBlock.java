package model;

import java.io.Serializable;

public interface SimpleBlock extends Serializable {
    String toString();
    String getStringRepresentation();
    String getCurrentHash();
    String getPreviousHash();
    double getExecutionTime();
    int getId();
}
