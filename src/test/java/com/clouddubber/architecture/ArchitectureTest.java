package com.clouddubber.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

class ArchitectureTest {
    @Test void domainShouldNotDependOnSpring(){ var c=new ClassFileImporter().importPackages("com.clouddubber"); ArchRuleDefinition.noClasses().that().resideInAPackage("..domain..").should().dependOnClassesThat().resideInAPackage("org.springframework..").check(c); }
    @Test void presentationNotAccessRepoDirectly(){ var c=new ClassFileImporter().importPackages("com.clouddubber"); ArchRuleDefinition.noClasses().that().resideInAPackage("..presentation..").should().dependOnClassesThat().resideInAPackage("..infra.persistence..").check(c); }
}
