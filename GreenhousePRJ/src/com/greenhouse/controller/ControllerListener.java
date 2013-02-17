package com.greenhouse.controller;

import com.greenhouse.base.Message;

public interface ControllerListener {
    public void notifyUDPMsg(Message message);
}
