package com.abrastat.runners;

import com.abrastat.general.Messages;

public class SingleInstanceMain {

    public static void main(String[] args) {

        new GSCGameRunner(100000);

        Messages.gameOver();
        System.exit(0);

    }
}
