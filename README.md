# Energy_Effecient-_Comm_In_WSM
This is a Java based project. In this project I designed and implemented an energy efficient communication application for wireless sensor networks.

A wireless sensor network consists of a set of sensor devices that communicate with each other via wireless links. Building an infrastructure for wireless sensor network that guarantees energy efficient communication is an important problem. Assume that we want to deploy a sensor network in an area to take temperature measurements and collect some statistics such as minimum, average, or maximum temperature in the area. We know about the topology information for a sensor network deployment.

One of the sensor nodes in the network is marked as a sink node. The sink node will issue queries into the network to collect a desired temperature statistic (min/average/max) from the network. One critical requirement in collecting this statistic is to ensure an energy efficient approach to disseminate the query to other sensor nodes and to ensure an energy efficient approach to collect the desired statistic from the nodes in the network.

My main job in this project was to come up with a design for a spanning tree construction algorithm for efficient dissemination of query messages and to come up with a design for a protocol for energy efficient collection of the requested statistics in the network.

HOW TO EXECUTE THE PROJECT:

The sinkNode.java is for the sink node and node.java is for the rest of the nodes. To compile and run the whole program. We need to do the following steps:

For nodes(i.e. for a non-sink node):

1.Compile "node.java" using the following command at the command prompt:

javac node.java

2. Open the cmd prompts as per the requirement- number of cmd prompts = number of nodes

3. For executing/running the file  use this command:

java node

4. Now it will ask for the respective port number and IP Address

According to the topologies that I have taken, you need to enter : "localhost" as ip address and say "6554" as the port number


For sink node:

1. Open the cmd prompt and compile the sinkNode.java using the following command:

javac sinkNode.java

2. After compiling, use to following command to execute: 

java sinkNode

3.	Now it will ask for the port number and IP Address of the sink node. According to the topologies taken, enter "loclahost" for ip address and "6553" as the port number.

4. A message will come regarding the type of the request user wants to make:
 
type= 1, 2 or 3 for average, maximum or minimum temperature respectively

The output will be displayed at the sink node console.

LIMITATIONS:
1. All the numeric values are of integer type so for calculating the average, only the answer in its integer form will be displayed.
 
2. It is tiring and difficult to open all the terminals and manually type in all the ip addresses and port numbers for the different nodes. A script can be made and all the output can be stored in a file to make things easy.
