package org.koreait.controller;
import org.koreait.dto.Member;

import java.util.List;

public abstract class Controller {
    protected static Member loginedMember = null;

    public abstract void doAction(String cmd, String actionMethodName);

    public static boolean isLogined() {
        return loginedMember != null;
    }

    public void makeTestData() {
    }
}