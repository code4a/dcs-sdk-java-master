package com.code4a.dueroslib.devicemodule.cmccgateway;

import com.code4a.dueroslib.devicemodule.cmccgateway.message.ControlDevicePayload;
import com.code4a.dueroslib.devicemodule.cmccgateway.message.ControlGatewayPayload;
import com.code4a.dueroslib.devicemodule.system.HandleDirectiveException;
import com.code4a.dueroslib.framework.BaseDeviceModule;
import com.code4a.dueroslib.framework.IMessageSender;
import com.code4a.dueroslib.framework.message.ClientContext;
import com.code4a.dueroslib.framework.message.Directive;
import com.code4a.dueroslib.framework.message.Payload;
import com.code4a.dueroslib.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/1/30.
 */

public class SmartGatewayDeviceModule extends BaseDeviceModule {

    static final String TAG = SmartGatewayDeviceModule.class.getSimpleName();

    private final List<ControlDeviceListener> listeners;

    public enum Command {
        ADD, REMOVE, BIND, UNBIND
    }

    public SmartGatewayDeviceModule(IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new ArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String name = directive.header.getName();
        if (name.equals(ApiConstants.Directives.Add.NAME)) {
            addSensorDevice(directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.Remove.NAME)) {
            removeSensorDevice(directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.Bind.NAME)) {
            bindGateway(directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.UnBind.NAME)) {
            unbindGateway(directive.getPayload());
        } else {
            String message = "SmartGateway cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    private void unbindGateway(Payload payload) {
        if (payload instanceof ControlGatewayPayload) {
            controlDevice(Command.UNBIND, ((ControlGatewayPayload) payload).getGateway(), null);
        } else {
            LogUtil.e(TAG, "payload class : " + payload.getClass().getSimpleName());
        }
    }

    private void bindGateway(Payload payload) {
        if (payload instanceof ControlGatewayPayload) {
            controlDevice(Command.BIND, ((ControlGatewayPayload) payload).getGateway(), null);
        } else {
            LogUtil.e(TAG, "payload class : " + payload.getClass().getSimpleName());
        }
    }

    private void removeSensorDevice(Payload payload) {
        if (payload instanceof ControlDevicePayload) {
            controlDevice(Command.REMOVE, ((ControlDevicePayload) payload).getDevice(), ((ControlDevicePayload) payload).getType());
        } else {
            LogUtil.e(TAG, "payload class : " + payload.getClass().getSimpleName());
        }
    }

    private void addSensorDevice(Payload payload) {
        if (payload instanceof ControlDevicePayload) {
            controlDevice(Command.ADD, ((ControlDevicePayload) payload).getDevice(), ((ControlDevicePayload) payload).getType());
        } else {
            LogUtil.e(TAG, "payload class : " + payload.getClass().getSimpleName());
        }
    }

    @Override
    public void release() {
        listeners.clear();
    }

    private void controlDevice(Command command, String deviceName, String type) {
        for (ControlDeviceListener listener : listeners) {
            listener.controlDevice(command, deviceName, type);
        }
    }

    public void addControlDeviceListener(ControlDeviceListener listener) {
        listeners.add(listener);
    }

    public void removeControlDeviceListener(ControlDeviceListener listener) {
        listeners.remove(listener);
    }

    public interface ControlDeviceListener {

        /**
         * 设备控制
         *
         * @param command    命令
         * @param deviceName 设备名称
         * @param type       设备类型
         */
        void controlDevice(Command command, String deviceName, String type);
    }
}
