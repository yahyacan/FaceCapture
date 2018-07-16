package com.yc.faceCapture;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.renderer.RenderHints;
import org.openimaj.image.typography.FontStyle;
import org.openimaj.image.typography.FontStyle.HorizontalAlignment;
import org.openimaj.image.typography.general.GeneralFont;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

/**
 * Hello world!
 *
 */
public class App {
	
	static int index = 0;
	
	public static void main(String[] args) throws VideoCaptureException {
		
		VideoCapture vc = new VideoCapture(320, 240);
		VideoDisplay<MBFImage> vd = VideoDisplay.createVideoDisplay(vc);
		vd.addVideoListener(new VideoDisplayListener<MBFImage>() {
			public void beforeUpdate(MBFImage frame) {
				FaceDetector<DetectedFace,FImage> fd = new HaarCascadeDetector(40);
				List<DetectedFace> faces = fd.detectFaces( Transforms.calculateIntensity(frame));

				final FontStyle<Float[]> gfs = new GeneralFont("Bank Gothic", Font.PLAIN).createStyle(frame
						.createRenderer(RenderHints.ANTI_ALIASED));
				gfs.setFontSize(15);
				gfs.setHorizontalAlignment(HorizontalAlignment.HORIZONTAL_CENTER);
				
				for( DetectedFace face : faces ) {
				    frame.drawShape(face.getBounds(), RGBColour.RED);
				    frame.drawText("Yahya", frame.getWidth() / 2, frame.getHeight() - 10, gfs);
				    
				    if (index == 0) {
						final Path outPath = Paths.get("/Users/yahya/Desktop/yahhh.jpg");
						try(OutputStream out = Files.newOutputStream(outPath)){
				            ImageUtilities.write(frame.extractROI(face.getBounds()), "JPG", out);
				        } catch (IOException ex) {
				            ex.printStackTrace();
				        }
						index = 1;
					}
				}
				
			
				
				
				
				
				
			}

			public void afterUpdate(VideoDisplay<MBFImage> display) {
			}
		});
		
		
		

	}
}
