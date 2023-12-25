package de.fhws.fiw.fds.sutton.server.utils;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;

public class UriHelper {

    public static int getLastPathElementAsId(final Link link) {
        if (link != null) {
            return getLastPathElementAsId(link.getHref());
        } else {
            return 0;
        }
    }

    public static int getLastPathElementAsId(final String uri) {
        final int lastSlash = uri.lastIndexOf("/");
        return Integer.parseInt(uri.substring(lastSlash + 1));
    }

}
