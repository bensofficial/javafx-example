package de.tum.in.test.api.io;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

@API(status = Status.INTERNAL)
public final class IOTesterManager implements IOManager<de.tum.in.test.api.io.IOTester> {

    private de.tum.in.test.api.io.IOTester ioTester;

    @Override
    public void beforeTestExecution(AresIOContext context) {
        ioTester = de.tum.in.test.api.io.IOTester.installNew(context.mirrorOutput(), context.maxStdOut());
    }

    @Override
    public void afterTestExecution(AresIOContext context) {
        de.tum.in.test.api.io.IOTester.uninstallCurrent();
        ioTester = null;
    }

    @Override
    public de.tum.in.test.api.io.IOTester getControllerInstance(AresIOContext context) {
        return ioTester;
    }

    @Override
    public Class<de.tum.in.test.api.io.IOTester> getControllerClass() {
        return IOTester.class;
    }
}
