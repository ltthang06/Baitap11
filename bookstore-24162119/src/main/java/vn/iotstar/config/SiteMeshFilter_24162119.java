package vn.iotstar.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFilter_24162119 extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {

        builder.addDecoratorPath("/admin/*", "admin.jsp");

        builder.addDecoratorPath("/*", "user.jsp");

        builder.addExcludedPath("/login");
        builder.addExcludedPath("/register");
        builder.addExcludedPath("/verify-otp");
        builder.addExcludedPath("/logout");
    }
}
