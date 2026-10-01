package vn.iotstar.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordUtil_24162119 {

    public static String md5(
            String value) {

        try {

            MessageDigest md =
                    MessageDigest
                            .getInstance("MD5");

            byte[] bytes =
                    md.digest(
                            value.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            StringBuilder result =
                    new StringBuilder();

            for (byte b : bytes) {

                result.append(
                        String.format(
                                "%02x",
                                b
                        )
                );
            }

            return result.toString();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}