package com.clouddubber.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ArchitectureTest {
    private static JavaClasses classes;

    @BeforeAll
    static void loadClasses() {
        classes = new ClassFileImporter().importPackages("com.clouddubber");
    }

    @Test
    void domainShouldNotDependOnSpring() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("org.springframework..")
                .check(classes);
    }

    @Test
    void domainShouldNotDependOnJpaOrJacksonOrHttp() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "jakarta.persistence..",
                        "com.fasterxml.jackson..",
                        "jakarta.servlet..",
                        "org.springframework.http.."
                )
                .check(classes);
    }

    @Test
    void presentationShouldNotAccessPersistenceDirectly() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..presentation..")
                .should().dependOnClassesThat().resideInAPackage("..infra.persistence..")
                .check(classes);
    }
}
