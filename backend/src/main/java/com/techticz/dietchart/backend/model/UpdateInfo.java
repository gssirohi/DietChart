package com.techticz.dietchart.backend.model;

import java.util.List;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 17/9/17.
 */

public class UpdateInfo {
    boolean isUpdateAvailable;
    boolean isForceUpdate;
    boolean isHardUpdate;
    String updateVersion;
    String updateTitle;
    String updateMessage;
    List<String> updates;
}
