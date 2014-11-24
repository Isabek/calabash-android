package sh.calaba.instrumentationbackend.actions.system;

import android.content.Context;
import android.net.ConnectivityManager;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Connection implements Action {
    @Override
    public Result execute(String... args) {
        String command = args[0];
        ConnectivityManager connectivityManager = (ConnectivityManager) InstrumentationBackend.instrumentation.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        String message = "";

        try {
            Method method = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);

            if (command.equals("enable")) {
                method.setAccessible(true);
                method.invoke(connectivityManager, true);

                message = "Connection is enabled";
            }

            if (command.equals("disable")) {
                method.setAccessible(true);
                method.invoke(connectivityManager, true);

                message = "Connection is disabled";
            }

        } catch (InvocationTargetException e) {

            message = e.getMessage();

        } catch (NoSuchMethodException e) {
            message = e.getMessage();
        } catch (IllegalAccessException e) {
            message = e.getMessage();
        }

        Result result = Result.successResult();
        result.setMessage(message);
        return result;
    }

    @Override
    public String key() {
        return "connection";
    }
}
