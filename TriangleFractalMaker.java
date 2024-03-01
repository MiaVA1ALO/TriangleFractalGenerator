import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.*;

public class TriangleFractalMaker extends JFrame
{
    static BufferedImage output;
    private JPanel canvas;

    // Main which starts the script
    public static void main(String[] args)
    {
        new TriangleFractalMaker();
    }

    public TriangleFractalMaker()
    {
        // App title
        super("Triangular fractal generator");

        // The width of the fractal to generate. Width = height
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter width of image: >");
        int width = sc.nextInt();

        // Makes the panel which has the actual image.
        this.canvas = new JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(output, 0, 0, null);
            }
        };
        canvas.setPreferredSize(new Dimension(width, width));

        // Sets up the scrollbar function
        JScrollPane jScrollPane = new JScrollPane(canvas);
        add(jScrollPane, BorderLayout.CENTER);
        setContentPane(jScrollPane);

        // The settings for the window
        setBounds(0, 0, 560, 560);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Outputs an image if the application is ended early
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                try
                {
                    ImageIO.write(output, "png", new File("image" + "1" + ".png"));
                }
                //Defines what happens if there is an error.
                catch(IOException exception)
                {
                    System.out.println("Error: " + exception);
                }
            }
        });
        setVisible(true);

        // Makes the image to be displayed + exported
        output = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);

        // Generate the initial triangle bounds
        output.setRGB(width / 2,0,237);
        output.setRGB(0,width - 1,237);
        output.setRGB(width - 1,width - 1,237);

        // Generate the starting pixel
        Random random = new Random();
        int lastX = random.nextInt(width);
        int lastY = random.nextInt(width);
        output.setRGB(lastX, lastY, 237);

        // A temporary value for determining which end of the triangle to use, randomizes every turn
        int temp;
        for(int i = 0; i < width * width; i++)
        {
            temp = random.nextInt(3);

            // Random colour generator
            Color colourTemp = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

            //To tell the user how close the picture is to finishing
            System.out.println(i);

            // Middle vertice
            if(temp == 0)
            {
                lastX = midpoint(width / 2, lastX);
                lastY = midpoint(0, lastY);

                output.setRGB(lastX, lastY, colourTemp.getRGB());
            }
            // Bottom left vertice
            else if(temp == 1)
            {
                lastX = midpoint(width - 1, lastX);
                lastY = midpoint(width - 1, lastY);

                output.setRGB(lastX, lastY, colourTemp.getRGB());
            }
            // Bottom right vertice
            else
            {
                lastX = midpoint(0, lastX);
                lastY = midpoint(width, lastY);

                output.setRGB(lastX, lastY, colourTemp.getRGB());
            }
            // Repaints the image every turn so that it's done live
            super.repaint();
        }

        // Overwrites the image with the new one
        try
        {
            ImageIO.write(output, "png", new File("image" + "1" + ".png"));
        }
        //Defines what happens if there is an error.
        catch(IOException exception)
        {
            System.out.println("Error: " + exception);
        }
        System.out.println("Finished generating");
    }

    // Made things a lot easier when generating the midpoint.
    private static int midpoint(int one, int two)
    {
        return (one + two) / 2;
    }

}
