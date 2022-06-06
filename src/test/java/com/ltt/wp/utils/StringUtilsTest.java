package com.ltt.wp.utils;

import com.qiniu.util.UrlSafeBase64;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testEmpty() {
        String base64Empty = UrlSafeBase64.encodeToString("");
        Assert.assertEquals("", base64Empty);
    }
}
