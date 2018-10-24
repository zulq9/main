package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.image.ImageView;

/**
 * Helper methods for dealing with {@code ImageView}.
 */
public class ImageViewUtil {

    /**
     * Returns the {@code Image} of the currently loaded page in the {@code imageView}.
     */
    public static String getLoadedImage(ImageView imageView) {
        if (imageView.getImage() == null) {
            return "";
        } else {
            return imageView.getImage().getUrl();
        }
    }

    /**
     * If the {@code browserPanelHandle}'s {@code WebView} is loading, sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilBrowserLoaded(BrowserPanelHandle browserPanelHandle) {
        new GuiRobot().waitForEvent(browserPanelHandle::isLoaded);
    }
}
