package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#photo";

    private boolean isWebViewLoaded = true;

    private String lastRememberedImage;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        ImageView imageView = getChildNode(BROWSER_ID);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public String getLoadedUrl() {
        return ImageViewUtil.getLoadedImage(getChildNode(BROWSER_ID));
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberUrl() {
        lastRememberedImage = getLoadedUrl();
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isUrlChanged() {

        return !lastRememberedImage.equals(getLoadedUrl());
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
