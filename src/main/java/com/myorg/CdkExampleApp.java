package com.myorg;

import software.amazon.awscdk.core.App;

import java.util.Arrays;

public class CdkExampleApp {
    public static void main(final String[] args) {
        App app = new App();

        new CdkExampleStack(app, "CdkExampleStack");

        app.synth();
    }
}
