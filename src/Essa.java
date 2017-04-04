/**
 * Created by João Miguel on 03/04/2017.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class Essa
{
    public int saturate (int valor)
    {
        if (valor < 0)
            return 0;
        else if (valor > 255)
            return 255;
        else
            return valor;
    }

    public int[] calcularHistograma (BufferedImage img)
    {
        int [] histograma = new int [256];
        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color point = new Color(img.getRGB(x, y));
                int red = point.getRed ();
                histograma[red] += 1;
            }
        }

        return histograma;

    }

    public int menorValor (int[] histo)
    {
        for (int i = 0; i < histo.length; i++)
        {
            if (histo[i] != 0)
                return i;
        }
        return 0;
    }

    public int[] calcularHistogramaAcumulado(int[] histo)
    {
        int [] array = new int [256];
        array[0] = histo[0];

        for (int i = 0; i < histo.length; i++)
        {
            array[0] = histo[1] + array[i - 1];
        }
    }

    public int[] calcularMapaCores (int[] histo, int pix)
    {
        int [] ColorMap = new int [256];
        int [] acumulado = calcularHistogramaAcumulado(histo);
        float menor = menorValor (histo);

        for (int i = 0; i < histo.length; i++)
        {
            ColorMap[i] = Math.round(((acumulado[i] - menor) / (pix - menor)) * 255);
        }
        return ColorMap;
    }


    public BufferedImage bright(BufferedImage img, float intensity)
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(),BufferedImage.TYPE_INT_RGB);
        int [] p = calcularHistograma(img);
        int [] q = calcularMapaCores(p);

        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color g = new Color (img.getRGB(x, y));
                int red = g.getRed();
                int tom = q[red];
                Color f = new Color (tom, tom, tom);
                out.setRGB(x, y, f.getRGB());
            }
        }
        return img;
    }

    public void run() throws IOException {
        File PATH = new File("C:\\Users\\João Miguel\\Desktop\\WallPapers");
        BufferedImage Nn = ImageIO.read(new File(PATH, "NntTiz.jpg"));
        BufferedImage newT = bright(Nn, 0.5f);
        ImageIO.write(newT, "jpg", new File(PATH,"NnTz.jpg"));
    }

    public static void main(String[] args) throws IOException
    {
        Essa Joao = new Essa();
        Joao.run();
    }
}