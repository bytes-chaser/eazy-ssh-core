# eazy-ssh-core
Highly-customizable Framework for simplifying SSH interactions.

## Adding
### Maven
```xml
<!-- https://mvnrepository.com/artifact/io.github.bytes-chaser/eazy-ssh-core -->
<dependency>
    <groupId>io.github.bytes-chaser</groupId>
    <artifactId>eazy-ssh-core</artifactId>
    <version>1.0.0</version>
</dependency>
```
### Gradle
```gradle
// https://mvnrepository.com/artifact/io.github.bytes-chaser/eazy-ssh-core
implementation 'io.github.bytes-chaser:eazy-ssh-core:1.0.0'
```

## Implementations
* [JSch](https://github.com/bytes-chaser/eazy-ssh-jsch) (over [ JSch library mwiede fork](https://github.com/mwiede/jsch))

## Usage
### Context (ESSHContext)
Context is a library central component. It is used for registering SSH Clients, parsers, and defining default settings for associated SSH clients.
The number of SSH contexts is up to the user as well as static definition or dynamic creation. It is possible to create one generic context, several contexts with different default settings, etc.

```java
        // Generic context
        ESSHContext genericCtx = new ESSHContextImpl();
        
        
        ESSHContext portCtx = new ESSHContextImpl();
        // sets default SSH port to 5867.
        portCtx.setPort(5867); 
```

### Client(ESSHClient)
The client is an interface for retrieving SSH data. 
All the interface methods are parsed into SSH commands from method name declaration or assigned @Exec annotation.
Method arguments overriding Context definitions. If some setting is not defined in method arguments either in the context, it will lead to exception throwing. In the default case, the method return type should be declared as String, but if you want to parse SSH output to some object just after the method call, you can specify another return type and register a parser for this type in the client context.

```java
public interface SimpleClient extends ESSHClient {

   /*
    Executes: ls
    Returns: String output of ls
    Context overrides: host, user, password
   */
   String ls(String host, String user, String pass);

   /*
    Executes: free -m | grep Mem: | awk '{printf \"#%d__%d#\", $2, $3}'
    Returns: MemoryInfo object if context contains parser for it.
    Context overrides: nothing, using context defaults.
   */
   @Exec(commands = "free -m | grep Mem: | awk '{printf \"#%d__%d#\", $2, $3}'")
   MemoryInfo getMemoryInfo();
   
   /*
    Executes: free -m | grep Mem: | awk '{printf \"#%d__%d#\", $2, $3}' and then 
    "top -bn2 | grep '%Cpu' | tail -1 | grep -P '(....|...) id,'|awk '{print 100-$8}'"
    Returns: String output of both commands, because outputParseStartIndex=0. By default returning only last command output
    Context overrides: host
   */
   @Exec(outputParseStartIndex = 0, commands = {
       "free -m | grep Mem: | awk '{printf \"#%d__%d#\", $2, $3}'", 
       "top -bn2 | grep '%Cpu' | tail -1 | grep -P '(....|...) id,'|awk '{print 100-$8}'"
   })
   String getSystemInfo(String host);
}
``` 

### Parser(ESSHParser)
Parsers are optional context additions that allow clients within context have methods return types other than String. If the main interest of executing SSH commands is other than working with SSH plain text, it makes sense to define parsers, so it will allow to not write point-cut functionality for parsing after getting SSH output.

```java
public static class MemoryInfoParser implements ESSHParser<MemoryInfo> {
    Pattern TOTAL_PTN = Pattern.compile("#(.*)__");
    Pattern USED_PTN = Pattern.compile("__(.*)#");

    @Override
    public MemoryInfo parse(String output) {
      Matcher totalMatcher = TOTAL_PTN.matcher(output);
      Matcher usedMatcher  = USED_PTN.matcher(output);

      String total = totalMatcher.group(1);
      String used  = usedMatcher.group(1);

      return new MemoryInfo(total, used);
    }
}
```

### Set it up
All the clients and parsers should be defined in the context before use. 
The last step is to define the SSH implementation used for the context clients.
All officially known implementation listed [above](https://github.com/bytes-chaser/eazy-ssh-core/edit/main/README.md#implementations)
In the example used [JSch](https://github.com/bytes-chaser/eazy-ssh-jsch) implementation

```java
        // New context with defaults
        ESSHContext context = new ESSHContextImpl();
        context.setHost(host);
        context.setUser(user);
        context.setPass(pass);
        
        // registers client and parser in the context
        ESSHContext context = context
            .register(SimpleClient.class)
            .parser(MemoryInfo.class, new MemoryInfoParser())
            .create(Jsch::new)
```
### Using clients
To use the client it should be got from the context and then used for getting outputs using SSH.
```java
    ...
    SimpleClient client = context.client(SimpleClient.class);
    String ls = client.ls(host, user, pass);
    MemoryInfo memInfo = client.getMemoryInfo();
    String sysInfo = client.getSystemInfo(host)
    ...
```
# Thanks for attention to the project, I hope it will make your SSH life easier :)
