/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pranjal
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class node {

    static Integer[] status_list = new Integer[100];
    static Integer[] rad_list = new Integer[100];
    static Integer[] x_list = new Integer[100];
    static Integer[] y_list = new Integer[100];
    static String[] node_list = new String[100];
    static String[] temp_list = new String[100];
    static String[] ip_list = new String[100];
    static String[] port_list = new String[100];
    static String[] line_list = new String[100];
    static int count = 0;
    static int whoI = 0;
    static String host = null;
    static int localport;

    public static void main(String[] args) throws IOException {
        try {
            //if(args.length != 2) throw new IllegalArgumentException();	
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the port no");
            localport = sc.nextInt();
            System.out.println("Enter the ip address");
            host = sc.next();
            System.out.println("Starting server for " + host + " on port " + localport);
                    //host = args[0];
            //localport = Integer.parseInt(args[1]);
            node parser = new node("D:\\MSCS\\Fall_14\\ACNKamilSarac\\Project\\Final Project\\Topology(1).txt");
            parser.processLineByLine();
            //log("Done.");
            //System.out.println("Connected...");

            

            runServer(host, localport);
        } catch (IllegalArgumentException e1) {
            System.err.println("Use: java UDPProxyServer <host> <remoteport> <localport>");
        }
    }

    public node(String aFileName) {
        fFilePath = Paths.get(aFileName);
    }

    public final void processLineByLine() throws IOException {
        try (Scanner scanner1 = new Scanner(fFilePath, ENCODING.name())) {
            //ArrayList status_list = new ArrayList();
//            Integer[] status_list = new Integer[100];
//            Integer[] rad_list = new Integer[100];
//            Integer[] x_list = new Integer[100];
//            Integer[] y_list = new Integer[100];
//            String[] node_list = new String[100];
//            String[] temp_list = new String[100];
//            String[] ip_list = new String[100];
//            String[] port_list = new String[100];
//            String[] line_list = new String[100];
//            int count = 0;

            while (scanner1.hasNextLine()) {
                // processLine(scanner1.nextLine());
                String aLine = scanner1.nextLine();
                //line_list.add(aLine);
                //use a second Scanner to parse the content of each line 
                Scanner scanner = new Scanner(aLine);
                scanner.useDelimiter(",");
                if (scanner.hasNext()) {
                    //assumes the line has a certain structure
                    node_list[count] = scanner.next();
                    x_list[count] = scanner.nextInt();
                    y_list[count] = scanner.nextInt();
                    ip_list[count] = scanner.next();
                    port_list[count] = scanner.next();
                    temp_list[count] = scanner.next();
                    rad_list[count] = scanner.nextInt();
                    status_list[count] = scanner.nextInt();
                    //System.out.println(node_list[count]);
                    count++;
                } //log("Name is : " + quote(status.trim()) + ", and Value is : " + quote(radius.trim()));
                else {
                    System.out.println("Empty or invalid line. Unable to process.");
                }
            }
            InetAddress ia = InetAddress.getByName(host);
            String conv_ia = ia.toString();
            System.out.println("IP-->" + ia);

            String conv_ip = "";

            for (int i = 0; i < port_list.length; i++) {
                if (port_list[i] != null) {
                    if (Integer.parseInt(port_list[i]) == localport) {
                        String ipaddr = ip_list[i];
                        InetAddress list_ia = InetAddress.getByName(ipaddr);
                        System.out.println("The corresponding ip address is" + list_ia);
                        conv_ip = list_ia.toString();
                    }

                    if (conv_ip.equals(conv_ia) && Integer.parseInt(port_list[i]) == localport) {
                        System.out.println("I am the NODE-->" + node_list[i]);
                        whoI = i;

                    }
                }

            }
        }
    }

    public static void runServer(String host, int localport)
            throws IOException {

        DatagramSocket ds = new DatagramSocket(localport);

		// Buffers for recieve a request and return the real server answer.
        // If we receive more then 1024 bytes from the client
        // they will be discard.
        byte[] request = new byte[999999];
        byte[] reply = new byte[999999];
        int temp_value = 0;
        int temp_count = 0;
        while (true) {

            try {

                // (1) receive data from client
                DatagramPacket from_client = new DatagramPacket(request, request.length);
                ds.receive(from_client);
                System.out.print("Somebody conected: ");
                                // Need to create another buffer with the size 
                // of bytes that we really recieved from client
                byte[] real_request = new byte[from_client.getLength()];
                for (int i = 0; i < from_client.getLength(); i++) {
                    real_request[i] = request[i];
                }
                String real_request9 = new String(real_request);
                                //System.out.println("Message from client: "+real_request9);
                //Reading topology file

                real_request9 = real_request9.substring(1, real_request9.length() - 1);
//
                String lineWithoutSpaces = real_request9.replaceAll("\\s+", "");

                real_request9 = lineWithoutSpaces;

                String real_req[] = real_request9.split(",");

                              //  real_req[real_req.length-1]= real_req[real_req.length-1].substring(0, 1);
                //System.out.println("String converted to String array");
                //print elements of String array
               /* for (String real_req1 : real_req) {
                    System.out.println(real_req1);
                }*/
            // 1D to 2D  
                // Not to be implemented here
                // Its just for testing purpose only.
                int size = real_req.length;
                //System.out.println(size);

                int type = Integer.parseInt(real_req[size - 1]);
                int count_point = Integer.parseInt(real_req[size - 2]);

            //int third_break_point = Integer.parseInt(total_array[size-2]);
                //int second_break_point = Integer.parseInt(total_array[size-3]);
                //int first_break_point = Integer.parseInt(total_array[size-4]);
                String[][] list_neighbours_point = new String[count_point][count_point];
                String[][] list_children_point = new String[count_point][count_point];
                String[] list_parents_point = new String[count_point];
                for (int i = 0; i < real_req.length; i++) {
                    if (real_req[i].equals("null")) {
                        real_req[i] = null;
                    }
                }

                int k = 0;
                for (int i = 0; i < count_point; i++) {
                    for (int j = 0; j < count_point; j++) {
                        list_neighbours_point[i][j] = real_req[k];

                        k++;
                    }
                }
                for (int i = 0; i < count_point; i++) {
                    for (int j = 0; j < count_point; j++) {
                        list_children_point[i][j] = real_req[k];
                        k++;
                    }
                }
                for (int i = 0; i < count_point; i++) {
                    list_parents_point[i] = real_req[k];
                    k++;

                }
                //System.out.println(k);
                //System.out.println("Children of node " + node_list[whoI] + " are: ");

                String[] children_node = new String[10];
                k = 0;
                for (int j = 0; j < count_point; j++) {
                    if (list_children_point[whoI][j] != null) {
                        //System.out.println(list_children_point[whoI][j]);
                        children_node[k] = list_children_point[whoI][j];
                        //System.out.println(" Port Number "+port_list[j]);
                    }
                    k++;
                }

                int flag = 0;
                for (k = 0; k < children_node.length; k++) {
                    if (children_node[k] != null) {
                        flag = flag + 1;
                    }
                }
                System.out.println("Number of children: " + flag);
                //String send_leaf;
                if (flag == 0) {
                    // We have found the leaf node
                    InetAddress address = from_client.getAddress();
                    int port = from_client.getPort();
                    String send_leaf = temp_list[whoI] + ",1";
                    DatagramPacket to_client
                            = new DatagramPacket(send_leaf.getBytes(), send_leaf.getBytes().length, address, port);
                    ds.send(to_client);
                    System.out.println("Info sent");
                    return;
                }
                //System.out.println("HI");
                String[] receive_from_child1 = null;
                int max=-51;
                int max1=0;
                int min=1000;
                int min1=0;
                for (k = 0; k < children_node.length; k++) {
                  //  System.out.println("Hello");
                    if (children_node[k] != null) {
                     //   System.out.println("Hello1");
                        
                        for (int i = 0; i < node_list.length; i++) {

                            if (node_list[i] != null) {

                            //System.out.println(children_node[k]);
                       //           System.out.println("HI1");
                                if (children_node[k].equals(node_list[i])) {
                                    System.out.println("Port Number of " + children_node[k] + " is:" + port_list[i]);

                                    String receive_from_child = runClient1(ip_list[i], Integer.parseInt(port_list[i]), real_req);
                                    receive_from_child1 = receive_from_child.split(",");

                                    temp_count++;
                                }
                            }
                            
                        }
//                        System.out.println("hi"); 
                    }
                   // System.out.println("HI");
                    
                    if (type==1)
                    {
                    if (children_node[k] != null) {
                        temp_value = temp_value + (Integer.parseInt(receive_from_child1[0]) * Integer.parseInt(receive_from_child1[1])); 
                    }
                    }
                    
                    else if (type==2)
                    {
                     //   System.out.println("HI");
                        if (children_node[k] != null) {
                       if (max <= Integer.parseInt(receive_from_child1[0]))
                       {
                          
                           max=Integer.parseInt(receive_from_child1[0]);
                           
                       }
                       else
                       {
                           max= max;
                       }
                        }
                    }
                     else if (type==3)
                    {
                       // System.out.println("HI");
                        if (children_node[k] != null) {
                       if (min >= Integer.parseInt(receive_from_child1[0]))
                       {
                          
                           min=Integer.parseInt(receive_from_child1[0]);
                           
                       }
                       else
                       {
                           min= min;
                       }
                        }
                    }
                }
                if (type==2)
                {
                    if (max>= Integer.parseInt(temp_list[whoI]))
                    {
                        max1=max;
                    }            
                    else
                    {
                        max1= Integer.parseInt(temp_list[whoI]);
                    }
                }
                if (type==3)
                {
                    if (min<= Integer.parseInt(temp_list[whoI]))
                    {
                        min1=min;
                    }            
                    else
                    {
                        min1= Integer.parseInt(temp_list[whoI]);
                    }
                }

				// (2) sending the received data to the server
//				InetAddress IPAddress = InetAddress.getByName(host);
//				DatagramPacket sendPacket =
//					new DatagramPacket(real_request,real_request.length,IPAddress,remoteport);
//				ds.send(sendPacket);
//					
                // (3) reading the server answer
			/*	DatagramPacket from_server = new DatagramPacket(reply,reply.length);
                 ds.receive(from_server);
                 byte[] real_reply = new byte[from_server.getLength()];
                 for(int i=0;i<from_server.getLength();i++) real_reply[i]=reply[i];
                 String real_reply9= new String(real_reply);
                 System.out.println("Message from server: "+real_reply9); */
                // (4) returning that answer to the client
               // System.out.println("hi");
                if (type==1)
                {
                if (temp_count == flag) {
                    int avg_temp = (temp_value + (Integer.parseInt(temp_list[whoI]))) / (temp_count + 1);
                    String avg_temp_str = Integer.toString(avg_temp) + "," + Integer.toString(temp_count + 1);
                    InetAddress address = from_client.getAddress();
                    int port = from_client.getPort();
                    DatagramPacket to_client
                            = new DatagramPacket(avg_temp_str.getBytes(), avg_temp_str.getBytes().length, address, port);
                    ds.send(to_client);
                }
                }
                else if (type==2)
                {
                    String max_temp_str= Integer.toString(max1)+",1";
                    InetAddress address = from_client.getAddress();
                    int port = from_client.getPort();
                    DatagramPacket to_client
                            = new DatagramPacket(max_temp_str.getBytes(), max_temp_str.getBytes().length, address, port);
                    ds.send(to_client);
                }
                else if (type==3)
                {
                    String min_temp_str= Integer.toString(min1)+",1";
                    InetAddress address = from_client.getAddress();
                    int port = from_client.getPort();
                    DatagramPacket to_client
                            = new DatagramPacket(min_temp_str.getBytes(), min_temp_str.getBytes().length, address, port);
                    ds.send(to_client);
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println(e);
            }
        }//end-while
    }

    private static String runClient1(String host1, int remoteport1, String[] total_array) throws IOException {
        String modified_sentence1 = null;
        String modifiedSentence = null;
        try {
            //System.out.print("Please enter a message: ");
            BufferedReader inFromUser = new BufferedReader(new StringReader(Arrays.toString(total_array)));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(host1);
            //int MAX_SIZE = Integer.MAX_VALUE;
            byte[] sendData = new byte[999999];
            byte[] receiveData = new byte[999999];
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, remoteport1);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            modifiedSentence = new String(receivePacket.getData());
            String modifiedSentence1 = null;
            if (modifiedSentence.charAt(2)==','){
            modifiedSentence1 = modifiedSentence.substring(0, 4);
            }
            if (modifiedSentence.charAt(3)==','){
            modifiedSentence1 = modifiedSentence.substring(0, 5);
            }
            modified_sentence1 = modifiedSentence1;
//System.out.println(modifiedSentence1);

            /*  String modifiedSentence2[]=modifiedSentence1.split(","); 
             System.out.println(modifiedSentence2[0]);
             System.out.println(modifiedSentence2[1]);
         
             System.out.println("FROM children:" + modifiedSentence1);
             modified_sentence1=Integer.parseInt(modifiedSentence2[0])*Integer.parseInt(modifiedSentence2[1]); */
        //  System.out.println("Hi1"); 
            //System.out.println("FROM children:" + (modified_sentence1+1));
            //System.out.println("Hi2");
            clientSocket.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
        return modified_sentence1;
    }

    protected void processLine(String aLine) {
    }
    // PRIVATE 
    private final Path fFilePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static void log(Object aObject) {
        System.out.println(String.valueOf(aObject));
    }

    private String quote(String aText) {
        String QUOTE = "'";
        return QUOTE + aText + QUOTE;
    }
}
