package com.viacom.test.vimn.common.listeners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Parameters;

import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;

public class TestAnnotationTransformer implements IAnnotationTransformer {
    
    @SuppressWarnings({"rawtypes"})
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    	// disable a test if it doesn't match our specific runtime requirements.
    	List<Annotation> testAnnotations = Arrays.asList(testMethod.getAnnotations());
    	List<String> parameterAnnotations = new ArrayList<String>();
    	
    	for (Annotation testAnnotation : testAnnotations) {
    		if (testAnnotation instanceof Parameters) {
    			parameterAnnotations.add(testAnnotation.toString());
    		}
    	}
    	
    	Boolean isMobileOS = false;
    	for (String parameterAnnotation : parameterAnnotations) {
    		if (parameterAnnotation.contains(ConfigProps.MOBILE_OS)) {
    		    isMobileOS = true;
    		    break;
    		}
    	}
    	
    	Boolean isApp = false;
    	for (String parameterAnnotation : parameterAnnotations) {
    		if (parameterAnnotation.contains(ParamProps.ALL_APPS)) {
    		    isApp = true;
    		    break;
    		} else if (parameterAnnotation.contains(ConfigProps.APP_NAME)) {
    			isApp = true;
    			break;
    		}
    	}
    	
    	if (!isMobileOS || !isApp) {
    		annotation.setEnabled(false);
    	}
    	
    	// mark the test for retry analysis
    	IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(TestListeners.class);
        }
		
    }
    
} 
