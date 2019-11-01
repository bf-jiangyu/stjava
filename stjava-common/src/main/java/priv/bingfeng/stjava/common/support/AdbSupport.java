package priv.bingfeng.stjava.common.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class AdbSupport {

    private static final Log LOG = LogFactory.getLog(AdbSupport.class);

    private static final String PHONE_BASE = "/storage/sdcard0";

    public static boolean checkDevice(String deviceId) {
        return getDevices().contains(deviceId);
    }

    public static List<String> getDevices() {
        List<String> rtn = new ArrayList<>();
        List<String> lines = execCommand("adb devices", true);
        for (int i = 1, size = lines.size() - 1; i < size; i++) {
            rtn.add(lines.get(i).split("\\s", 2)[0]);
        }

        return rtn;
    }

    public static String getTopActivity(String deviceId) {
        List<String> lines = execCommand(String.format("adb -s %s shell dumpsys activity | grep mResume", deviceId), true);
        return lines.size() == 0 ? "" : lines.get(0);
    }

    public static boolean checkTopActivity(String deviceId, String activity) {
        return getTopActivity(deviceId).contains(activity);
    }

    public static void tap(String deviceId, int x, int y) {
        execCommand(String.format("adb -s %s shell input tap %d %d", deviceId, x, y));
    }

    public static void swipe(String deviceId, int x, int y, int moveToX, int moveToY, int speed) {
        execCommand(String.format("adb -s %s shell input touchscreen swipe %s %s %s %s %s", deviceId, x, y, moveToX, moveToY, speed));
    }

    public static void back(String deviceId) {
        keyevent(deviceId, "KEYCODE_BACK");
    }

    private static void keyevent(String deviceId, String event) {
        execCommand(String.format("adb -s %s shell input keyevent %s" , deviceId, event));
    }

    public static void sendFile(String deviceId, String from, String to) {
        execCommand(String.format("adb -s %s push %s %s/%s", deviceId, from, PHONE_BASE, to));
    }

    public static boolean checkFile(String deviceId, String file) {
        List<String> lines = execCommand(String.format("adb -s %s shell ls %s/%s", deviceId, PHONE_BASE, file), true);
        for (String line : lines) {
            if (line.contains("No such file or director"))
                return false;
        }
        return true;
    }

    public static void copyFile(String deviceId, String from, String to) {
        execCommand(String.format("adb -s %s pull %s/%s %s", deviceId, PHONE_BASE, from, to));
    }

    public static void delFile(String deviceId, String file) {
        execCommand(String.format("adb -s %s shell rm %s/%s", deviceId, PHONE_BASE, file));
    }

    public static void appOpen(String deviceId, String launcher) {
        execCommand(String.format("adb -s %s shell am start -n %s", deviceId, launcher));
    }

    public static void appClose(String deviceId, String packageName) {
        execCommand(String.format("adb -s %s shell am force-stop %s", deviceId, packageName));
    }

    public static void sendBroadcast(String deviceId, String broadcast) {
        execCommand("adb -s " + deviceId + " shell am broadcast -a " + broadcast);
    }

    private static List<String> execCommand(String cmd) {
        return execCommand(cmd, false);
    }
    private static List<String> execCommand(String cmd, boolean rtn) {
        return execCommand(cmd, rtn, false);
    }
    private static List<String> execCommand(String cmd, boolean rtn, boolean debug) {
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            if (rtn || debug) {
                List<String> rtnList = new ArrayList<>();

                try (LineNumberReader input = new LineNumberReader(new InputStreamReader(pos.getInputStream()))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        rtnList.add(line);
                        if (debug)
                            System.out.println(line);
                    }
                }

                if (rtn)
                    return rtnList;
            }
        } catch (IOException | InterruptedException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(String.format("[adb-support]-[运行命令出错]-[%s]", cmd), e);
            }
        }

        return new ArrayList<>(0);
    }

}
