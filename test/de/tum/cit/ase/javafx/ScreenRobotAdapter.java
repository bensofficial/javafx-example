package de.tum.cit.ase.javafx;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class ScreenRobotAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(ScreenRobotAdapter.class);
    private Object robot;

    public ScreenRobotAdapter() {
        initializeRobot();
    }

    private void initializeRobot() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Class<?> applicationClass = Class.forName("com.sun.glass.ui.Application");
                Method getApplicationMethod = applicationClass.getDeclaredMethod("GetApplication");
                Object application = getApplicationMethod.invoke(null);

                Method createRobotMethod = applicationClass.getDeclaredMethod("createRobot");
                this.robot = createRobotMethod.invoke(application);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize MonocleRobot", e);
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public BufferedImage captureScreen(int x, int y, int width, int height) {
        if (robot == null) {
            throw new IllegalStateException("Robot not initialized.");
        }
        AtomicReference<BufferedImage> imageRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Method getScreenCaptureMethod = robot.getClass().getDeclaredMethod("getScreenCapture",
                        int.class, int.class, int.class, int.class, int[].class, boolean.class);
                getScreenCaptureMethod.setAccessible(true); // Ensure accessibility

                int[] pixelArray = new int[width * height];

                getScreenCaptureMethod.invoke(robot, x, y, width, height, pixelArray, false);

                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                image.setRGB(0, 0, width, height, pixelArray, 0, width);
                imageRef.set(image);
            } catch (Exception e) {
                throw new RuntimeException("Failed to capture screen", e);
            } finally {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return imageRef.get();
    }

    public void saveScreenshot(String filePath, int x, int y, int width, int height) {
        try {
            BufferedImage image = captureScreen(x, y, width, height);
            ImageIO.write(image, "png", new File(filePath));
        } catch (Exception e) {
            LOG.error("Failed to save screenshot", e);
        }
    }
}
