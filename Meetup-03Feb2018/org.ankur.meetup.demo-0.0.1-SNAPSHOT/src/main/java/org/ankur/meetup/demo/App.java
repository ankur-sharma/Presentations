package org.ankur.meetup.demo;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        classifierExample();
    }

	private static void classifierExample() {
//		String movie = "american gang";
		String movie = "shiva";
		
		String genre = ClassifierTrainer.classify(movie.split(" "));
		System.out.println(genre);
	}
}
