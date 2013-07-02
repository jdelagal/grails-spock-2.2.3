
## with grails 2.0.0 #

> All the test cases pass using grails 2.0.0


<pre><code>
+ git checkout grails-2.0.0;
+ export GRAILS_HOME=...;
+ export PATH=$GRAILS_HOME/bin:$PATH
+ grails clean;
// all pass
+ grails test-app --echoOut UserControllerSpec UserControllerSpockMockSpec UserServiceIntegrationSpec UserServiceIntegrationSpockMockSpec; 
</code> </pre>


## with grails 2.2.3 ##

> The spock unit mock tests seem to work in isolation, but if combined with any other test cases that use Grails Mock they seem to fail. i.e if I run UserControllerSpockMockSpec in isolation it worksbut if run together with "UserControllerSpec UserControllerSpockMockSpec" it will cause failure.. 

<pre><code>
+ git checkout master;
+ export GRAILS_HOME=...
+ export PATH=$GRAILS_HOME/bin:$PATH
+ grails clean;
</code> </pre>

<pre><code>
// works in isolation
+ grails test-app --echoOut UserControllerSpockMockSpec; 
// one test case fails 
+ grails test-app --echoOut UserControllerSpec;  
// failures in both specs
+ grails test-app --echoOut UserControllerSpec UserControllerSpockMockSpec;  
</code></pre>

> for integration specs the spock Mock tests seem to fail in isolation also..

<pre><code>
// fails
+ grails test-app --echoOut UserServiceIntegrationSpec;  
// fails
+ grails test-app --echoOut UserServiceIntegrationSpockMockSpec 
</code></pre>


