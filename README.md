
## with grails 2.0.0 #

All the test cases pass using grails 2.0.0


<pre><code>
+ git checkout grails-2.0.0;
+ export GRAILS_HOME=...;
+ export PATH=$GRAILS_HOME/bin:$PATH
+ grails clean;
+ grails test-app --echoOut UserControllerSpec UserControllerSpockMockSpec UserServiceIntegrationSpec UserServiceIntegrationSpockMockSpec; // all pass
</code>
</pre>


## with grails 2.2.3 ##

> The spock unit mock tests seem to work in isolation, but if combined with any other test cases that use Grails Mock they seem to fail. i.e if I run UserControllerSpockMockSpec in isolation it worksbut if run together with "UserControllerSpec UserControllerSpockMockSpec" it will cause failure.. 

+ git checkout master;
+ export GRAILS_HOME=...
+ export PATH=$GRAILS_HOME/bin:$PATH
+ grails clean;

### Unit Specs ###

+ grails test-app --echoOut UserControllerSpockMockSpec; // Works if the same failed test case above is not mixed with Grails @Mock

+ grails test-app --echoOut UserControllerSpec;  // One of the test case fails

>> | Failure:  create a Domain collaborator using Mock(com.grails.UserControllerSpec)
>> |  java.lang.ClassCastException: com.grails.User cannot be cast to net.sf.cglib.proxy.Factory
>>	at org.spockframework.mock.runtime.ProxyBasedMockFactory$CglibMockFactory.createMock(ProxyBasedMockFactory.java:93)

+ grails test-app --echoOut UserControllerSpec UserControllerSpockMockSpec;  // Unfortunately, running the specs together causes UserControllerSpockMockSpec to fail too..


### Integration Specs ###

> Spock Mock Integration tests seem to fail in isolation also. see UserServiceIntegrationSpockMockSpec

+ grails test-app --echoOut UserServiceIntegrationSpec;  // One of the test case fails

>> | Failure:  create User Mock using spock(com.grails.UserServiceIntegrationSpec)
>> |  java.lang.ClassCastException: com.grails.User cannot be cast to net.sf.cglib.proxy.Factory
>>	at org.spockframework.mock.runtime.ProxyBasedMockFactory$CglibMockFactory.createMock(ProxyBasedMockFactory.java:93)

+ grails test-app --echoOut UserServiceIntegrationSpockMockSpec // Still fails, in Unit tests it seems to work in isolation

>>| Failure:  create User Mock using spock(com.grails.UserServiceIntegrationSpockMockSpec)
>>|  java.lang.ClassCastException: com.grails.User cannot be cast to net.sf.cglib.proxy.Factory
>>	at org.spockframework.mock.runtime.ProxyBasedMockFactory$CglibMockFactory.createMock(ProxyBasedMockFactory.java:93)



