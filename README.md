# cas-shop-manager
A computer accessories shop management system. 

This project was developed as coursework for an object oriented programming course at Loughborough University. It was awarded 94% and has not been updated since submission. The coursework specification has not been included to avoid copyright infringement.

The project contains a single piece of software which acts as both as a storefront and management interface for a fictional shop. The shop sells only keyboards and mice, which come in different varieties. A user can be either an admin or a customer. All users can view the shop's inventory. Admins can add new items. Customers can add items to a basket and go through a mock checkout process. All activities are recorded in a log file.

This project was primarily an introduction to object oriented programming methods. The focus was on proper class design and interaction. Therefore, other aspects of the system such as the GUI, databases, and payment processing systems are primitive at best and were never indended as examples of real world practices.

# What's that?
This repository includes:
* ClassDiagram.jpg - A UML class diagram showing all classes used in the system.
* Jar - A directory containing the compiled program and accompanying flatfile databases:
  * cas.jar - The compiled system.
  * ActivityLog.txt - A log of user activities.
  * Stock.txt - A flatfile database of shop inventory.
  * UserAccounts.txt - A flatfile database of user account info.
* Source Files - A directory containing all of the source Java files used to make the program.

# Running the program
To run the program: download the jar directory, open it, and run 'java -jar cas.jar'.
