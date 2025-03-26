package in.jvapps.disable_battery_optimization.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

public class PrefUtils {

    private static final String PREF_NAME = "disable_battery_optimization_prefs";
    private static final String TAG = "PrefUtils";

    private static SharedPreferences getPrefs(@NonNull Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Сохраняет значение в SharedPreferences.
     */
    public static void saveToPrefs(@NonNull Context context, @NonNull String key, @NonNull Object value) {
        SharedPreferences prefs = getPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();

        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Double) {
            // SharedPreferences не поддерживает Double напрямую
            editor.putLong(key, Double.doubleToRawLongBits((Double) value));
        } else {
            Log.w(TAG, "Unsupported value type: " + value.getClass().getName());
            return;
        }

        editor.apply();
    }

    /**
     * Получает значение из SharedPreferences.
     */
    public static Object getFromPrefs(@NonNull Context context, @NonNull String key, @NonNull Object defaultValue) {
        SharedPreferences prefs = getPrefs(context);
        try {
            if (defaultValue instanceof String) {
                return prefs.getString(key, (String) defaultValue);
            } else if (defaultValue instanceof Integer) {
                return prefs.getInt(key, (Integer) defaultValue);
            } else if (defaultValue instanceof Boolean) {
                return prefs.getBoolean(key, (Boolean) defaultValue);
            } else if (defaultValue instanceof Long) {
                return prefs.getLong(key, (Long) defaultValue);
            } else if (defaultValue instanceof Float) {
                return prefs.getFloat(key, (Float) defaultValue);
            } else if (defaultValue instanceof Double) {
                return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits((Double) defaultValue)));
            } else {
                Log.w(TAG, "Unsupported default value type: " + defaultValue.getClass().getName());
                return defaultValue;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting value from prefs", e);
            return defaultValue;
        }
    }

    /**
     * Удаляет значение по ключу.
     */
    public static void removeFromPrefs(@NonNull Context context, @NonNull String key) {
        SharedPreferences prefs = getPrefs(context);
        prefs.edit().remove(key).apply();
    }

    /**
     * Проверяет наличие ключа.
     */
    public static boolean hasKey(@NonNull Context context, @NonNull String key) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.contains(key);
    }

    /**
     * Очищает все данные.
     */
    public static void clearAll(@NonNull Context context) {
        SharedPreferences prefs = getPrefs(context);
        prefs.edit().clear().apply();
    }
}