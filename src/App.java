import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.naming.directory.SearchResult;
import javax.swing.*;
import javax.swing.border.LineBorder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class App extends JFrame implements ActionListener {
    JFrame j;
    static JTextField t;
    static JButton searchButton;
    static JLabel image;
    static JLabel wind;
    static mainbackend n = new mainbackend();
    static String imgfile  = "D:/projectn1/javaapiproject/asset/search.png";

    public App(){
        
        setSize(600,800);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        addcomponenet();
        checkaction();

    }

    private  void addcomponenet(){

        //textfield
        t = new JTextField();
        t.setBounds(10, 50, 400, 50);
        Font customFont = new Font("Arial", Font.BOLD, 20);
        t.setFont(customFont);


        //search button with image
        searchButton = new JButton();
        searchButton.setBounds(450,50,70,50);
        ImageIcon searchicon = new ImageIcon(imgfile);
        JLabel imageLabel = new JLabel(searchicon);
        searchButton.add(imageLabel);



        JPanel searchpanel = new JPanel();
        searchpanel.setBounds(25,0,600,100);
        searchpanel.setLayout(null);
        searchpanel.add(t);
        searchpanel.add(searchButton);
        add(searchpanel);


        ImageIcon wheatherimg = new ImageIcon("D:/projectn1/javaapiproject/asset/clear.png");
        image = new JLabel();
        image.setIcon(wheatherimg);
        image.setText("26°C");
        Font customFont1 = new Font("Arial", Font.BOLD, 35);
        image.setFont(customFont1);
        image.setHorizontalTextPosition(JLabel.CENTER);
        image.setVerticalTextPosition(JLabel.BOTTOM);
        image.setHorizontalAlignment(JLabel.CENTER);
        image.setVerticalAlignment(JLabel.CENTER);
        image.setBounds(150,100,300,400);
        image.setIconTextGap(30);
        add(image);


        ImageIcon windimg = new ImageIcon("D:/projectn1/javaapiproject/asset/windspeed.png");
        wind = new JLabel(windimg);
        wind.setText(" 10 k/hr");
        wind.setFont(customFont);
        wind.setBounds(10,450,200,200);
        add(wind);






    }

    public  static void connect (String name){


        JSONObject object  = n.operation(name);

        String condition = (String) object.get("condition");
        String temperature = (String) object.get("temperature");
        String windspeed = (String) object.get("windspeed");

        image.setText(temperature + "°C");
        wind.setText(windspeed + "k/hr");
        //System.out.print(object.toString());
        //image.setima
        ImageIcon imgfile = imageselector(condition);
        image.setIcon(imgfile);
    }



    private static ImageIcon imageselector(String condition){
        ImageIcon image;
        if(condition.equalsIgnoreCase("clear"))
            image  = new ImageIcon("D:/projectn1/javaapiproject/asset/clear.png");
        else if(condition.equalsIgnoreCase("foggy")||condition.equalsIgnoreCase("Drizzle"))
            image  = new ImageIcon("D:/projectn1/javaapiproject/asset/humidity.png");
        else if(condition.equalsIgnoreCase("Freezing Drizzle")||condition.equalsIgnoreCase("Freezing Rain")||condition.equalsIgnoreCase("snow grains")||condition.equalsIgnoreCase("snow showers"))
            image  = new ImageIcon("D:/projectn1/javaapiproject/asset/snow.png");
        else if(condition.equalsIgnoreCase("Rain")||condition.equalsIgnoreCase("Rain Showers"))
            image  = new ImageIcon("D:/projectn1/javaapiproject/asset/rain.png");
        else if(condition.equalsIgnoreCase("Thenderstorm"))
            image  = new ImageIcon("D:/projectn1/javaapiproject/asset/thunderstorm.png");
        else
            image = null;

        return image;
    }

    public static void checkaction(){
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String  text  = t.getText();
                System.out.println(text);
                connect(text);
            }
        });

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
