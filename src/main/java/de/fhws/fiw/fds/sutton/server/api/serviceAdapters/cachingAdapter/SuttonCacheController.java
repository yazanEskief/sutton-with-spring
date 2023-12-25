package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.cachingAdapter;

public class SuttonCacheController {

    private boolean privateFlag;

    private boolean noCacheFlag;

    private boolean noStoreFlag;

    private boolean noTransformFlag;

    private boolean mustRevalidateFlag;

    private boolean proxyRevalidate;

    private int maxAge = -1;

    public SuttonCacheController() {
        privateFlag = false;
        noCacheFlag = false;
        noStoreFlag = false;
        noTransformFlag = true;
        mustRevalidateFlag = false;
        proxyRevalidate = false;
    }

    public boolean isPrivateFlag() {
        return privateFlag;
    }

    public boolean isNoCacheFlag() {
        return noCacheFlag;
    }

    public boolean isNoStoreFlag() {
        return noStoreFlag;
    }

    public boolean isNoTransformFlag() {
        return noTransformFlag;
    }

    public boolean isMustRevalidateFlag() {
        return mustRevalidateFlag;
    }

    public boolean isProxyRevalidate() {
        return proxyRevalidate;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public SuttonCacheController setPrivate(final boolean flag) {
        privateFlag = flag;
        return this;
    }

    public SuttonCacheController setNoCache(final boolean flag) {
        noCacheFlag = flag;
        return this;
    }

    public SuttonCacheController setNoStore(final boolean flag) {
        noStoreFlag = flag;
        return this;
    }

    public SuttonCacheController setNoTransform(final boolean flag) {
        noTransformFlag = flag;
        return this;
    }

    public SuttonCacheController setMustRevalidate(final boolean flag) {
        mustRevalidateFlag = flag;
        return this;
    }

    public SuttonCacheController setProxyRevalidate(final boolean flag) {
        proxyRevalidate = flag;
        return this;
    }

    public SuttonCacheController setMaxAge(final int maxAge) {
        this.maxAge = maxAge;
        return this;
    }
}
