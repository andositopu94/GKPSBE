package com.GKPS.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableMethodSecurity(prePostEnabled = false)
public class RbacConfig {
    //Hierarki:
    //- PENDETA >MAJELIS>SINTUA>JEMAAT
    //- MAJELIS >KETUA_SEKSI>SEKRETARIS_SEKSI>BENDAHARA_SEKSI>ANGGOTA
    //- MAJELIS >KETUA_SEKTOR>SEKRETARIS_SEKTOR>BENDAHARA_SEKTOR

    @Bean
    public RoleHierarchy roleHierarchy() {
        String hierarchy = """
            ROLE_PENDETA > ROLE_MAJELIS
            ROLE_MAJELIS > ROLE_SINTUA
            ROLE_MAJELIS > ROLE_SYAMAS
            ROLE_SINTUA > ROLE_JEMAAT
            ROLE_SYAMAS > ROLE_JEMAAT

            ROLE_MAJELIS > ROLE_KETUA_SEKSI
            ROLE_KETUA_SEKSI > ROLE_SEKRETARIS_SEKSI
            ROLE_SEKRETARIS_SEKSI > ROLE_BENDAHARA_SEKSI
            ROLE_BENDAHARA_SEKSI > ROLE_ANGGOTA

            ROLE_MAJELIS > ROLE_KETUA_SEKTOR
            ROLE_KETUA_SEKTOR > ROLE_SEKRETARIS_SEKTOR
            ROLE_SEKRETARIS_SEKTOR > ROLE_BENDAHARA_SEKTOR
            ROLE_BENDAHARA_SEKTOR > ROLE_ANGGOTA

            ROLE_KETUA_SEKSI > ROLE_SEKSI_BAPA
            ROLE_KETUA_SEKSI > ROLE_SEKSI_WANITA
            ROLE_KETUA_SEKSI > ROLE_SEKSI_PEMUDA
            ROLE_KETUA_SEKSI > ROLE_SEKSI_REMAJA
            ROLE_KETUA_SEKSI > ROLE_SEKSI_SEKOLAH_MINGGU

            ROLE_ANGGOTA > ROLE_JEMAAT
            ROLE_LAINNYA > ROLE_JEMAAT
            """;
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
    }

    /**
     * Expression Handler untuk Method Security
     * Memungkinkan penggunaan ekspresi seperti hasRole(), hasAnyRole(), dll
     */
    @Bean
    public DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

    /**
     * Expression Handler untuk Web Security
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

}
