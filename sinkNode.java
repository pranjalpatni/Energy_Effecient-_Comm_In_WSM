/**
 *
 * @author Pranjal
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Pranjal
 */
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
public class sinkNode {
       static String portn;
       static String ip_p;
       static String req_type;
       static int whoI = 0;
       static int avg_temp=0;
       static int type=0;
           static Integer[] status_list = new Integer[100];
           static Integer[] rad_list = new Integer[100];
           static Integer[] x_list = new Integer[100];
           static Integer[] y_list = new Integer[100];
           static String[] node_list = new String[100];
           static String[] temp_list = new String[100];
           static String[] ip_list = new String[100];
           static String[] port_list = new String[100];
           static String[] line_list = new String[100];
    public static void main(String... aArgs) throws Exception
   {
    Scanner sc = new Scanner(System.in);
            System.out.println("Enter the port no");
            //portn="6553";
            //ip_p="localhost";
            portn = sc.next();
            System.out.println("Enter the ip address");
            ip_p = sc.next();
            System.out.println("Starting server for " + ip_p + " on port " + portn);
            System.out.println("Enter what you want to find:");
            System.out.println("average:1; max:2; min:3");
            req_type = sc.next();
       try
       {
         //if(args.length != 2) throw new IllegalArgumentException();
         //String host1=args[0];
         //int remoteport1= Integer.parseInt(args[1]);
    sinkNode parser = new sinkNode("D:\\MSCS\\Fall_14\\ACNKamilSarac\\Project\\Final Project\\Topology(1).txt");
        String answer_temp=parser.processLineByLine();
        if (type==1)
        {
            String answer_temp1 = null;
            if(answer_temp.charAt(3)==','){
              answer_temp1 = answer_temp.substring(0, 5);  
            }
            if(answer_temp.charAt(4)==','){
              answer_temp1 = answer_temp.substring(0, 7);  
            }
                String answer[]= answer_temp1.split(",");
        int final_answer= (Integer.parseInt(answer[0])+(Integer.parseInt(temp_list[whoI])))/Integer.parseInt(answer[1]);
           System.out.println(final_answer);
        }
        else if (type==2)
        {
            System.out.println("Max temperature is :" + answer_temp);
        }
        else if (type==3)
        {
            System.out.println("Min temperature is :" + answer_temp);
        }
        
        
        
        log("Done.");
           //System.out.println("Connected to server...");
           
       }
       catch(IllegalArgumentException e1) {
			System.err.println("Use: java UDPProxyServer <host> <remoteport>");
               }
}
     public sinkNode(String aFileName) {
        fFilePath = Paths.get(aFileName);
    }
     public final String processLineByLine() throws IOException {
         int count = 0;
         int final_total_temp1=0;
        try (Scanner scanner1 = new Scanner(fFilePath, ENCODING.name())) {
            //ArrayList status_list = new ArrayList();
            
           // int count = 0;

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

                    //log("Name is : " + quote(status.trim()) + ", and Value is : " + quote(radius.trim()));
                } else {
                    log("Empty or invalid line. Unable to process.");
                }
            }

            int store = 0;
            int sink = 0;
            int[][] n_mat = new int[count][count];
            for (int i = 0; i < count; i++) {
                if (status_list[i] == 1) {
                    System.out.println("We have found a root node: " + node_list[i]);
                    sink = i;
                }
                //count--;
            }

            /// Swap sink node with first node in the list
            String node = node_list[0];
            int x = x_list[0];
            int y = y_list[0];
            String ip = ip_list[0];
            String port = port_list[0];
            String temp = temp_list[0];
            int rad1 = rad_list[0];
            int status = status_list[0];

            node_list[0] = node_list[sink];
            x_list[0] = x_list[sink];
            y_list[0] = y_list[sink];
            ip_list[0] = ip_list[sink];
            port_list[0] = port_list[sink];
            temp_list[0] = temp_list[sink];
            rad_list[0] = rad_list[sink];
            status_list[0] = status_list[sink];

            node_list[sink] = node;
            x_list[sink] = x;
            y_list[sink] = y;
            ip_list[sink] = ip;
            port_list[sink] = port;
            temp_list[sink] = temp;
            rad_list[sink] = rad1;
            status_list[sink] = status;

            
            //System.out.println("Port" + portn);
            //System.out.println("IP Address" + ip_p);
            InetAddress ia = InetAddress.getByName(ip_p);
            String conv_ia = ia.toString();
            System.out.println("IP-->" + ia);

            String conv_ip = "";

            
            for (int i = 0; i < port_list.length; i++) {
                if (port_list[i] != null) {
                    if (port_list[i].equals(portn)) {
                        String ipaddr = ip_list[i];
                        InetAddress list_ia = InetAddress.getByName(ipaddr);
                        System.out.println("The corresponding ip address is" + list_ia);
                        conv_ip = list_ia.toString();
                    }

                    if (conv_ip.equals(conv_ia) && port_list[i].equals(portn)) {
                        System.out.println("I am the NODE-->" + node_list[i]);
                        whoI = i;

                    }
                }

            }

/// Finding Neighbours,children and parents
            String[][] list_neighbours = new String[count][count];
            String[][] list_children = new String[count][count];
            String[] list_parents = new String[count];
            for (store = 0; store < count; store++) {
                int j = 0;
                for (int i = 0; i < count; i++) {

                    if (i != store) {

                        double distx = x_list[store] - x_list[i];
                        double disty = y_list[store] - y_list[i];
                        double dist = Math.sqrt(distx * distx + disty * disty);
                        double rad = (double) rad_list[store];
                        //System.out.println("Distance to node "+node_list[i]+" from node "+node_list[store] +" = "+dist);
                        if (dist <= rad) {
                            n_mat[store][i] = 1;
                            list_neighbours[store][j] = node_list[i];
                            list_children[store][j] = node_list[i];
                            // System.out.print(n_mat[i][store]);
                            //System.out.println("Distance to node "+node_list[i]+" from node "+node_list[store] +" = "+dist);
                            //System.out.println(node_list[store] + " neighbour " + node_list[i]);
                            j++;
                        } else {
                            n_mat[store][i] = 0;
                            //System.out.print(n_mat[i][store]);
                        }

                    }
                    //System.out.println();

                }

            }

            System.out.println();
            ArrayList<String> list_n = new ArrayList<String>();
            list_n.add(node_list[sink]);
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    if (list_children[i][j] != null) {
                        int flag = 0;
                        Iterator<String> iterator = list_n.iterator();
                        while (iterator.hasNext()) {
                            String element = iterator.next();
                            if (element == (list_children[i][j])) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            list_n.add(list_children[i][j]);
                        } else {
                            list_children[i][j] = null;
                        }

                    }
                }
            }
            System.out.println();
            for (int i = 0; i < count; i++) {
                if (node_list[i] != null) {
                    System.out.println("Children of " + node_list[i]);
                    for (int j = 0; j < count; j++) {

                        if (list_children[i][j] != null) {
                            System.out.println(list_children[i][j]);

                        }
                    }
                }
            }
            System.out.println();
            for (int i = 0; i < count; i++) {
                if (node_list[i] != null) {
                    System.out.println("Neighbours of " + node_list[i]);
                    for (int j = 0; j < count; j++) {

                        if (list_neighbours[i][j] != null) {
                            System.out.println(list_neighbours[i][j]);

                        }
                    }
                }
            }

            for (int i = 0; i < count; i++) {
                if (node_list[i] != null) {
                    System.out.println("Parent of " + node_list[i]);
                    for (int j = 0; j < count; j++) {
                        if (j == i) {
                            continue;
                        }
                        for (int k = 0; k < count; k++) {
                            if (list_children[j][k] != null) {
                                if (list_children[j][k].equals(node_list[i])) {
                                    System.out.println(node_list[j]);
                                    list_parents[i] = node_list[j];
                                }

                            }
                        }
                    }

                }
            }

            /// 2D to 1D
            int size_of_total = 2 * count * count + count + 2;
            String[] total_array = new String[size_of_total];
            int count1 = 0;
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    total_array[count1] = list_neighbours[i][j];
                    count1++;
                }
            }
            int first_break = count1;
            //System.out.println(count1);
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    total_array[count1] = list_children[i][j];
                    count1++;
                }
            }
            int second_break = count1;
            //System.out.println(count1);
            for (int i = 0; i < count; i++) {

                total_array[count1] = list_parents[i];
                count1++;

            }
            int third_break = count1;
            //System.out.println(count1);
            //total_array[count1] = Integer.toString(first_break);
            //total_array[count1+1] = Integer.toString(second_break);
            //total_array[count1+2] = Integer.toString(third_break);
            total_array[count1] = Integer.toString(count);
            total_array[count1 + 1] = req_type;

            // 1D to 2D  
            // Not to be implemented here
            // Its just for testing purpose only.
            int size = total_array.length;
           // System.out.println(size);

            type = Integer.parseInt(total_array[size - 1]);
            int count_point = Integer.parseInt(total_array[size - 2]);

            //int third_break_point = Integer.parseInt(total_array[size-2]);
            //int second_break_point = Integer.parseInt(total_array[size-3]);
            //int first_break_point = Integer.parseInt(total_array[size-4]);
            String[][] list_neighbours_point = new String[count_point][count_point];
            String[][] list_children_point = new String[count_point][count_point];
            String[] list_parents_point = new String[count_point];

            int k = 0;
            for (int i = 0; i < count_point; i++) {
                for (int j = 0; j < count_point; j++) {
                    list_neighbours_point[i][j] = total_array[k];
                    k++;
                }
            }
            for (int i = 0; i < count_point; i++) {
                for (int j = 0; j < count_point; j++) {
                    list_children_point[i][j] = total_array[k];
                    k++;
                }
            }
            for (int i = 0; i < count_point; i++) {
                list_parents_point[i] = total_array[k];
                k++;

            }
            //System.out.println(k);
            
                 System.out.println("Children of node " + node_list[whoI]+" are: ");

            String[] children_node = new String[10];
            k = 0;
            for (int j = 0; j < count_point; j++) {
                if (list_children_point[whoI][j] != null) {
                    System.out.println(list_children_point[whoI][j]);
                    children_node[k] = list_children_point[whoI][j];
                    //System.out.println(" Port Number "+port_list[j]);
                }
                k++;
            }
            
            
            
            int final_total_temp=0;
            int final_min_temp1=Integer.parseInt(temp_list[whoI]);
            int final_max_temp1=Integer.parseInt(temp_list[whoI]);
            for(k=0;k<children_node.length;k++)
                {
                    
                    if(children_node[k]!=null)
                    {
                    for(int i=0;i<node_list.length;i++)
                    {
                        if(node_list[i]!=null)
                        {
                            if(children_node[k].equals(node_list[i]))
                            {
                                System.out.println("Port Number of "+children_node[k]+" is:"+port_list[i]);
                           // int final_total_temp=0;     
                                if (type==1)
                                {
                            final_total_temp = final_total_temp+runClient1(ip_list[i], Integer.parseInt(port_list[i]), total_array);
                                }
                                else if (type==2)
                                {
                                   int final_max_temp =runClient1(ip_list[i], Integer.parseInt(port_list[i]), total_array);
                                   if (final_max_temp> final_max_temp1)
                                   {
                                       final_max_temp1 = final_max_temp;
                                   }
                                   else
                                   {
                                       final_max_temp1 = final_max_temp1;
                                   }
                                }
                                 else if (type==3)
                                {
                                   int final_min_temp =runClient1(ip_list[i], Integer.parseInt(port_list[i]), total_array);
                                   if (final_min_temp< final_min_temp1)
                                   {
                                       final_min_temp1=final_min_temp;
                                   }
                                   else
                                   {
                                       final_min_temp1=final_min_temp1;
                                   }
                                }
                            }
                        }
                    }
                    }
                }
            if (type==1){
                final_total_temp1= final_total_temp1+final_total_temp;
                return Integer.toString(final_total_temp1)+","+Integer.toString(count);
        }
            else if(type==2){
                return Integer.toString(final_max_temp1);
            }
            else if(type==3){
                return Integer.toString(final_min_temp1);
            }
        }
        
        return null;
    }
private static int runClient1(String host1, int remoteport1, String[] total_array) throws IOException {
   // int avg_temp=0;  
    try
      {
      //System.out.print("Please enter a message: ");
      BufferedReader inFromUser =new BufferedReader(new StringReader(Arrays.toString(total_array)));
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
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println(modifiedSentence);
      //
      
      if (type ==1)
      {
          String modifiedSentence1=null;
       if (modifiedSentence.charAt(2)==','){
            modifiedSentence1 = modifiedSentence.substring(0, 4);
            }
            if (modifiedSentence.charAt(3)==','){
            modifiedSentence1 = modifiedSentence.substring(0, 5);
            }
            String[] receive_from_child = modifiedSentence1.split(",");
      
      
      //
      //int modifiedSentence_1= Integer.parseInt(modifiedSentence);
      avg_temp=((Integer.parseInt(receive_from_child[0]))*(Integer.parseInt(receive_from_child[1])));
      //System.out.println("Average temperature of topology is:" + avg_temp);
      }
      else if (type==2)
      {
          String modifiedSentence1 = modifiedSentence.substring(0, 2);
          avg_temp= Integer.parseInt(modifiedSentence1);
      }
      else if (type==3)
      {
          String modifiedSentence1 = modifiedSentence.substring(0, 2);
          avg_temp= Integer.parseInt(modifiedSentence1);
      }
      clientSocket.close();
    }
      catch(Exception e) {
	System.out.println(e);
			}
    return avg_temp;
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

