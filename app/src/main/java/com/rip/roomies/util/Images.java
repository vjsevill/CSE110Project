package com.rip.roomies.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.rip.roomies.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * This class keeps track of all the images we need in our app.
 */
public class Images {
	private static final Logger log = Logger.getLogger(Images.class.getName());

	private static final int MAX_QUALITY = 100;

	/**
	 * Get a bitmap of the desired resource that is scaled down to the requested size so that
	 * less memory is used and does not cause significant overhead on the device.
	 * @param res The resources of the activity
	 * @param id The id of the resource to get bitmap of
	 * @param width The desired width of the resource
	 * @param height The desired height of the resource
	 * @return The resultant bitmap
	 */
	public static Bitmap getScaledDownBitmap(Resources res, int id, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, id, options);

		options.inSampleSize = getSampleSize(options, width, height);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, id, options);
	}

	/**
	 * Gets a scaled down bitmap based on an input stream.
	 * @param resolver The resolver of the activity
	 * @param image The image uri to get stream of
	 * @param width The desired width of the image
	 * @param height The desired height of the image
	 * @return The scaled down bitmap
	 * @throws IOException If the stream cannot be opened
	 */
	public static Bitmap getScaledDownBitmap(ContentResolver resolver, Uri image,
	                                         int width, int height) throws IOException {
		InputStream stream = resolver.openInputStream(image);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(stream, null, options);

		options.inSampleSize = getSampleSize(options, width, height);

		if (stream != null) {
			stream.close();
		}

		stream = resolver.openInputStream(image);
		options.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeStream(stream, null, options);

		if (stream != null) {
			stream.close();
		}

		return bmp;
	}

	public static Bitmap getScaledDownBitmap(byte[] image, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(image, 0, image.length, options);

		options.inSampleSize = getSampleSize(options, width, height);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(image, 0, image.length, options);
	}

	/**
	 * Get the sample size based off the options object and the desired width/height.
	 * @param options The options that contains image original size
	 * @param reqWidth The desired width of the image
	 * @param reqHeight The desired height of the image
	 * @return The inSampleSize that can be used by BitmapFactory to scale down image
	 */
	private static int getSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}
