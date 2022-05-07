# HW 9 - RMI Browser
The application prints out the objects registered in the RMI registry.

The program gets the address and port where the RMI registry runs as command-line arguments. 
The program connects to the registry and prints out a list of names of all objects registered in the registry
to the standard output. For each object, the program prints out all remotely callable methods (i.e., methods 
of all implemented interfaces that extend Remote). Each object name and each method are printed out on a 
separate line. Methods are printed using the `toString()` method of the `Method` class. Two spaces are printed
in front of each method. The objects are printed out sorted lexicographically by name; the methods are 
printed out sorted lexicographically according to the result of `toString()`.

For example, if the object implements the interface:
```java
package hello; 

interface Hello extends Remote { 
  void sayHello() throws RemoteException; 
  void sayMessage(String s) throws RemoteException; 
} 
```

and an object with this interface is registered in the registry using the name <b>hello</b>, 
then the program prints out:
```
hello 
  public abstract void hello.Hello.sayHello() throws java.rmi.RemoteException 
  public abstract void hello.Hello.sayMessage(java.lang.String) throws java.rmi.RemoteException
```
