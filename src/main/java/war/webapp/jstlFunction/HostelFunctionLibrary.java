package war.webapp.jstlFunction;

import java.lang.reflect.Method;

import java.lang.reflect.Modifier;

import com.sun.facelets.tag.AbstractTagLibrary;

public final class HostelFunctionLibrary extends AbstractTagLibrary {

          public final static String NAMESPACE = "http://hostelFunctions.test.by";

          public static final HostelFunctionLibrary INSTANCE = new HostelFunctionLibrary();

          public HostelFunctionLibrary() {

                    super(NAMESPACE);

                    try {

                              Method[] methods = HostelFunctions.class.getMethods();

                              for (int i = 0; i < methods.length; i++) {

                                        if (Modifier.isStatic(methods[i].getModifiers())) {

                                                  this.addFunction(methods[i].getName(), methods[i]);

                                        }

                              }

                    } catch (Exception e) {

                              throw new RuntimeException(e);

                    }

          }

}