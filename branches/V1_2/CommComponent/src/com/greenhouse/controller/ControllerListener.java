package com.greenhouse.controller;

import com.greenhouse.base.Message;

public interface ControllerListener {
    public void notifyMsg(Message message);
    public void initCommListener();
}
