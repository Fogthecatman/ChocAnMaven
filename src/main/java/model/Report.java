package model;

import java.sql.ResultSet;

public interface Report {
    String format(ResultSet rs);
}
