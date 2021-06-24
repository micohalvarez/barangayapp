package com.release.barangayapp.callback;

import com.release.barangayapp.model.Emergency;
import com.release.barangayapp.model.LogBook;

import java.util.ArrayList;

public interface LogBookCallback {
    void logBookCallback(ArrayList<LogBook> value);
}

