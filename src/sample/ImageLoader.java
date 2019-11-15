package sample;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import scala.collection.immutable.IndexedSeq;

public class ImageLoader {
    Mat matrixImagePixel;
    int height;
    int width;

    void Load(String path, int number) {
        int iter = 2;
        // matrixImagePixel= Imgcodecs.imread(path);
        matrixImagePixel = Imgcodecs.imread("C:\\Users\\KOma_rash\\Desktop\\P000" + number + ".jpg");
        Mat mat = new Mat();
        Imgproc.GaussianBlur(matrixImagePixel, matrixImagePixel, new Size(3, 3), 0, 0);
        ;
        Imgproc.GaussianBlur(matrixImagePixel, matrixImagePixel, new Size(3, 3), 0, 0);
        ;

        //        Imgproc.cvtColor(matrixImagePixel, matrixImagePixel, Imgproc.COLOR_RGB2HLS);
        Imgproc.cvtColor(matrixImagePixel, matrixImagePixel, Imgproc.COLOR_BGR2GRAY);
        Imgproc.erode(matrixImagePixel, matrixImagePixel, new Mat(), new Point(-1, -1), iter);
        //      Imgproc.erode(matrixImagePixel,matrixImagePixel,new Mat());

        Imgcodecs.imwrite("C:\\Users\\KOma_rash\\Desktop\\P0001460(2).jpg", matrixImagePixel);
        Imgproc.dilate(matrixImagePixel, matrixImagePixel, new Mat(), new Point(-1, -1), iter);
//      Imgproc.dilate(matrixImagePixel,matrixImagePixel,new Mat());
        Mat grad_x = new Mat();
        Imgcodecs.imwrite("C:\\Users\\KOma_rash\\Desktop\\P0001460)3).jpg", matrixImagePixel);
        Imgproc.Sobel(matrixImagePixel, grad_x, CvType.CV_8U, 1, 0);
        Mat abs_grad_x = new Mat();
        Core.convertScaleAbs(grad_x, abs_grad_x);
        Mat grad_y = new Mat();
        Imgproc.Sobel(matrixImagePixel, grad_y, CvType.CV_8U, 0, 1);
        Mat abs_grad_y = new Mat();
        Core.convertScaleAbs(grad_y, abs_grad_y);
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 1, mat);


        Imgcodecs.imwrite("C:\\Users\\KOma_rash\\Desktop\\P0001460)5).jpg", mat);
        // Imgproc.Sobel(matrixImagePixel,mat,1,	CV_SCHARR,	1,	1,BORDER_DEFAULT);
        int startIntense = 193;
        int endIntense = 255;
        Core.inRange(matrixImagePixel, new Scalar(startIntense, startIntense, startIntense), new Scalar(endIntense, endIntense, endIntense), mat);
        Size size=mat.size();
        int totalBytes = (int)(mat.total() * mat.elemSize());
        byte[] buffer = new byte[totalBytes];
        mat.get(0,0,buffer);
        buffer =figureInfo$.MODULE$.kmeans(buffer,size);
         mat.put(0,0,buffer);
                Imgcodecs.imwrite("C:\\Users\\KOma_rash\\Desktop\\image\\P000" + number + ".jpg", mat);
            }

    }

