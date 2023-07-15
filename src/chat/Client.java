
package chat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;
import javax.swing.JScrollPane;

public class Client implements ActionListener{
    JTextField text1;
     static JTextArea a1;
     static Box vertical= Box.createVerticalBox();
     
     static JFrame f= new JFrame();
    static DataOutputStream dout;
    
    Client(){
        f.setLayout(null);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(26, 93, 26));
        p1.setBounds(0,0,450,60);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(23, 23, Image.SCALE_DEFAULT);
        ImageIcon i3= new  ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,23,23);
        p1.add(back);
        //Action
        back.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent ae){
        System.exit(0);
        
        }
        });
        
        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/client.png"));
        Image i5 = i4.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i6= new  ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(45,15,35,35);
        p1.add(profile);
        
        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i9= new  ImageIcon(i8);
        JLabel vc = new JLabel(i9);
        vc.setBounds(300,20,25,25);
        p1.add(vc);
        
         ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i12= new  ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(355,20,25,25);
        p1.add(call);
        
        ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15= new  ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(410,20,10,25);
        p1.add(more);
        
        JLabel name =new JLabel("Client");
        name.setBounds(95, 22, 150, 20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(name);
        
        a1=new JTextArea();
        a1.setBounds(5, 60, 439, 555);
        a1.setBackground(new Color(250, 227, 146));
       f.add(a1);
       
        
      
        
        text1=new JTextField();
        text1.setBounds(5,635,310,40);
        text1.setFont(new Font ("SAN_SERIF", Font.PLAIN,16));
        f.add(text1);
        
       JButton send=new JButton("Send");
       send.setBounds(320, 635, 123, 40);
       send.setBackground(new Color(26, 93, 26));
       send.setForeground(Color.WHITE);
       send.addActionListener(this);
       send.setFont(new Font ("SAN_SERIF", Font.PLAIN,16));
       f.add(send);
       f.setBackground(new Color(250, 227, 146));
        f.setSize(450,685);
        f.setLocation(800,37);
        f.setUndecorated(true);
        f.setVisible(true);
        
      
}
    public void actionPerformed(ActionEvent ae){
        try{
            
         String out=text1.getText();
        
        JPanel p2= formatLabel(out);
         p2.setBackground(new Color(250, 227, 146));
       
        a1.setLayout(new BorderLayout());
        JPanel right =new JPanel(new BorderLayout());
        right.setBackground(new Color(250, 227, 146));
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);//for multiple msg ,vertical alignment is needed
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical,BorderLayout.PAGE_START);
        dout.writeUTF(out);
        text1.setText("");
         f.repaint();
       f.invalidate();
        f.validate();
        }
        catch(Exception e){
        e.printStackTrace();}
        
    }
    public static JPanel formatLabel(String out){
        JPanel panel =new JPanel();
       panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
       
       JLabel output =new JLabel("<html><p style=\"width:100px\">"+out+"</p></html>");
       panel.setBackground(new Color(250, 227, 146));
        
    

        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(241, 201, 59));
        
        
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
       Calendar cal =Calendar.getInstance();
       SimpleDateFormat sdf=new  SimpleDateFormat("HH:mm");
       
       JLabel time=new JLabel();
       time.setText(sdf.format(cal.getTime()));
       panel.add(time);
        
        return panel;
    }
    public static void main(String[] args){
        new Client();
        
        try{
        Socket clientSocket =new Socket("localhost",1234);
         DataInputStream din = new DataInputStream(clientSocket.getInputStream());
         dout = new DataOutputStream(clientSocket.getOutputStream());
           while(true){
                a1.setLayout(new BorderLayout());
                String msg= din.readUTF();
                JPanel panel =formatLabel(msg);
                JPanel left=new JPanel(new BorderLayout());
               left.setBackground(new Color(250, 227, 146));
                left.add(panel,BorderLayout.LINE_START);
               vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));

                a1.add(vertical,BorderLayout.PAGE_START);
                f.validate();
                
                }
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
    
    }
    
}
