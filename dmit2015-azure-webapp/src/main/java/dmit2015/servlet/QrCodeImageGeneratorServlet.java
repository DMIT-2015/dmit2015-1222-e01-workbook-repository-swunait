package dmit2015.servlet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet(name = "QrCodeImageGeneratorServlet", value = "/QrCodeImageGeneratorServlet")
public class QrCodeImageGeneratorServlet extends HttpServlet {

    private byte[] generateQrCodeImage(String link, int imageSize) throws WriterException, IOException {
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix currentBitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, imageSize, imageSize);
        var imageOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(currentBitMatrix, "PNG", imageOutputStream);
        byte[] imageData = imageOutputStream.toByteArray();
        return imageData;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String urlString = request.getParameter("url");
        String imageSizeString = request.getParameter("imageSize");
        int imageSize = (imageSizeString == null || imageSizeString.isBlank()) ? 114 : Integer.parseInt(imageSizeString);

        if (urlString == null || urlString.isBlank()) {
            response.getWriter().write("Missing <strong>url</strong> parameter");
        } else {
            String downloadImage = request.getParameter("download");
            if (downloadImage != null) {
                response.setHeader("Content-disposition", "attachment;filename=linkQrCode");
            }
            try {
                byte[] imageContent = generateQrCodeImage(urlString, imageSize);
                response.setContentType("image/png");
                response.setContentLength(imageContent.length);
                response.getOutputStream().write(imageContent);
            } catch (WriterException e) {
                e.printStackTrace();
                response.getWriter().write("Error generating QRCode image");
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
